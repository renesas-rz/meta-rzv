require recipes-kernel/linux-libc-headers/linux-libc-headers.inc

KERNEL_URL = " \
    git://github.com/renesas-rz/rz_linux-cip.git"
BRANCH = "rzv2l-cip41"
SRCREV = "006f1feaa0f91982fcf1dde79c35e7cce0010f43"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRC_URI = "${KERNEL_URL};branch=${BRANCH}"

FILESEXTRAPATHS_prepend := "${THISDIR}/../linux/linux-renesas:"

S = "${WORKDIR}/git"

DEPENDS = "bison-native flex-native"

do_install_armmultilib_append () {
	oe_multilib_header asm/bpf_perf_event.h asm/kvm_para.h
}
