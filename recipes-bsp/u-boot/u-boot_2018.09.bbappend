FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# if you want to add new patch file, please set the patch file under the directory "u-boot"


# Start RZV2M auto-generated variables
PR = "r1"

SRC_URI_append_r9a09g011gbg += "\
  file://0001-updated-uboot-rzv2m.patch \
  file://0002-chg-l2cache-clear.patch \
  file://0003-add-bank-settings.patch \
  file://0004_apply_new-memorymap-kernel-area-address.patch \
  file://0005-fixed-bug-auto-boot-when-no-serial.patch \
  file://0006-modified-text-section-address.patch \
  file://0007-update-memory-map.patch \
  "
EXE_PYTHON = "python3"
PY_FILE = "${B}/${config}/scripts/sum.py"


do_compile_append(){
	rm -f ${B}/${config}/u-boot_param.bin
	python3 ${B}/${config}/source/scripts/sum.py ${B}/${config}/${UBOOT_SYMLINK} ${B}/${config}/u-boot_param.bin
}

do_install_append () {
	install -m 644 ${B}/${config}/u-boot_param.bin ${D}/boot/u-boot_param.bin
}

do_deploy_append  () {
	install -m 644 ${B}/${config}/u-boot_param.bin ${DEPLOYDIR}/u-boot_param.bin
}

# Finish RZG auto-generated variables

