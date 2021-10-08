FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# if you want to add new patch file, please set the patch file under the directory "bootloader/patch"


SRC_URI_append += "\
 file://patch/0001-fixed-rebuild-error.patch \
 file://patch/0002-modified-ddr-training.patch \
 "

