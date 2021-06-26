#
# base recipe: meta/recipes-core/eglibc/eglibc_2.19.bb
# base branch: daisy
#

require glibc.inc

LICENSE = "GPLv2 & LGPLv2.1 & ISC"
LIC_FILES_CHKSUM = " \
file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
file://LICENSES;md5=e9a558e243b36d3209f380deb394b213 \
"

DEPENDS += "gperf-native"

# Exclude patch that apply for old version:
# mips-rld-map-check.patch
# initgroups_keys.patch
# eglibc_fix_findidx_parameters.patch
# fileops-without-wchar-io.patch
# fix-tibetian-locales.patch
# CVE-2014-5119.patch
#
# Exclude GLRO_dl_debug_mask.patch because debian source code (2.19-10) 
# does not support RTLD debug
#
# Exclude 0001-eglibc-menuconfig-support.patch
#         0002-eglibc-menuconfig-hex-string-options_debian.patch
#         0003-eglibc-menuconfig-build-instructions.patch
# because Debian distro does not support kconfig-frontend
# correct-valencia-locale-supported_debian.patch:
# 	This patch is fix "QA Issue: locale-base-ca-es is listed in PACKAGES multiple times"
SRC_URI += " \
	${NATIVESDKFIXES} \
	file://eglibc-svn-arm-lowlevellock-include-tls.patch \
	file://IO-acquire-lock-fix.patch \
	file://etc/ld.so.conf \
	file://generate-supported.mk \
	file://glibc.fix_sqrt2.patch \
	file://multilib_readlib.patch \
	file://ppc-sqrt_finite.patch \
	file://ppc_slow_ieee754_sqrt.patch \
	file://add_resource_h_to_wait_h.patch \
	file://0001-R_ARM_TLS_DTPOFF32.patch \
	file://ppce6500-32b_slow_ieee754_sqrt.patch \
	file://grok_gold.patch \
	file://0013-sysdeps-gnu-configure.ac-handle-correctly-libc_cv_ro.patch \
	file://correct-valencia-locale-supported_debian.patch \
	file://0001-Fix-error-undefined-reference-to-libgcc_s_resume.patch \
	file://0010-eglibc-run-libm-err-tab.pl-with-specific-dirs-in-S.patch \
	file://0023-Define-DUMMY_LOCALE_T-if-not-defined.patch \
"

NATIVESDKFIXES ?= ""
NATIVESDKFIXES_class-nativesdk = "\
	file://0001-nativesdk-glibc-Look-for-host-system-ld.so.cache-as-.patch \
	file://0002-nativesdk-glibc-Fix-buffer-overrun-with-a-relocated-.patch \
	file://0003-nativesdk-glibc-Raise-the-size-of-arrays-containing-.patch \
	file://relocate-locales.patch \
"

B = "${WORKDIR}/build-${TARGET_SYS}"

do_debian_patch_append() {
	# Set empty because kconfig-frontend is not supported
	echo "config:" >> ${S}/Makeconfig

	# No documentation
	sed -i -e "s:manual::" ${S}/Makeconfig
}

PACKAGES_DYNAMIC = ""

# the -isystem in bitbake.conf screws up glibc do_stage
BUILD_CPPFLAGS = "-I${STAGING_INCDIR_NATIVE}"
TARGET_CPPFLAGS = "-I${STAGING_DIR_TARGET}${includedir}"

GLIBC_BROKEN_LOCALES = " _ER _ET so_ET yn_ER sid_ET tr_TR mn_MN gez_ET gez_ER bn_BD te_IN es_CR.ISO-8859-1"

FILESPATH = "${@base_set_filespath([ '${FILE_DIRNAME}/glibc-${PV}', '${FILE_DIRNAME}/glibc', '${FILE_DIRNAME}/files', '${FILE_DIRNAME}' ], d)}"

#
# For now, we will skip building of a gcc package if it is a uclibc one
# and our build is not a uclibc one, and we skip a glibc one if our build
# is a uclibc build.
#
# See the note in gcc/gcc_3.4.0.oe
#

python __anonymous () {
    import re
    uc_os = (re.match('.*uclibc$', d.getVar('TARGET_OS', True)) != None)
    if uc_os:
        raise bb.parse.SkipPackage("incompatible with target %s" %
                                   d.getVar('TARGET_OS', True))
}

EXTRA_OECONF = "--enable-kernel=${OLDEST_KERNEL} \
                --without-cvs --disable-profile \
                --disable-debug --without-gd \
                --enable-clocale=gnu \
                --enable-add-ons \
                --with-headers=${STAGING_INCDIR} \
                --without-selinux \
                --enable-obsolete-rpc \
                --with-kconfig=${STAGING_BINDIR_NATIVE} \
		--disable-profile \
		--without-gd \
		--without-cvs \
		--enable-add-ons=${GLIBC_ADDONS} \
                ${GLIBC_EXTRA_OECONF}"

EXTRA_OECONF += "${@get_libc_fpu_setting(bb, d)}"

do_patch_append() {
    bb.build.exec_func('do_fix_readlib_c', d)
}

# for mips glibc now builds syscall tables for all abi's
# so we make sure that we choose right march option which is
# compatible with o32,n32 and n64 abi's
# e.g. -march=mips32 is not compatible with n32 and n64 therefore
# we filter it out in such case -march=from-abi which will be
# mips1 when using o32 and mips3 when using n32/n64

TUNE_CCARGS_mips := "${@oe_filter_out('-march=mips32', '${TUNE_CCARGS}', d)}"
TUNE_CCARGS_mipsel := "${@oe_filter_out('-march=mips32', '${TUNE_CCARGS}', d)}"

do_fix_readlib_c () {
	sed -i -e 's#OECORE_KNOWN_INTERPRETER_NAMES#${EGLIBC_KNOWN_INTERPRETER_NAMES}#' ${S}/elf/readlib.c
}

do_configure () {
# override this function to avoid the autoconf/automake/aclocal/autoheader
# calls for now
# don't pass CPPFLAGS into configure, since it upsets the kernel-headers
# version check and doesn't really help with anything
	(cd ${S} && gnu-configize) || die "failure in running gnu-configize"
	find ${S} -name "configure" | xargs touch
	CPPFLAGS="" oe_runconf
}

rpcsvc = "bootparam_prot.x nlm_prot.x rstat.x \
          yppasswd.x klm_prot.x rex.x sm_inter.x mount.x \
          rusers.x spray.x nfs_prot.x rquota.x key_prot.x"

do_compile () {
	# -Wl,-rpath-link <staging>/lib in LDFLAGS can cause breakage if another glibc is in staging
	unset LDFLAGS
	base_do_compile
	(
		cd ${S}/sunrpc/rpcsvc
		for r in ${rpcsvc}; do
			h=`echo $r|sed -e's,\.x$,.h,'`
			rm -f $h
			${B}/sunrpc/cross-rpcgen -h $r -o $h || bbwarn "${PN}: unable to generate header for $r"
		done
        )
	echo "Adjust ldd script"
	if [ -n "${RTLDLIST}" ]
	then
		prevrtld=`cat ${B}/elf/ldd | grep "^RTLDLIST=" | sed 's#^RTLDLIST="\?\([^"]*\)"\?$#\1#'`
		if [ "${prevrtld}" != "${RTLDLIST}" ]
		then
			sed -i ${B}/elf/ldd -e "s#^RTLDLIST=.*\$#RTLDLIST=\"${prevrtld} ${RTLDLIST}\"#"
		fi
	fi
}

require glibc-package.inc

BBCLASSEXTEND = "nativesdk"
FILES_${PN}-doc += "${datadir}"
