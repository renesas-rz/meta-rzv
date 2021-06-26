require recipes-core/images/core-image-minimal.bb
require core-image-renesas-base.inc
require core-image-bsp.inc
require core-image-docker.inc

IMAGE_FEATURES += "ssh-server-dropbear"
IMAGE_INSTALL_append = " python python3 "

export SOURCE_DIR="${THISDIR}/environment-setup"
fakeroot append_setup () {
    install -d ${SDK_OUTPUT}/${SDKPATH}/sysroots/${SDK_SYS}/environment-setup.d/
    cp ${SOURCE_DIR}/environment-setup-append.sh ${SDK_OUTPUT}/${SDKPATH}/sysroots/${SDK_SYS}/environment-setup.d/
}
SDK_POSTPROCESS_COMMAND_prepend = " append_setup; "
