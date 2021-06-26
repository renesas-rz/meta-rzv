SUMMARY = "Boot loader for RZV2M"
SECTION = "Loader"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PV = "v1.0.0"
PR = "r1"

S = "${WORKDIR}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
FILES_${PN}  = "/boot "

inherit deploy

SRC_URI = "file://rzv2m-bootloader-v100.tar.gz"

do_compile() {
	cd ${S}/source
	rm -f  ./1st_loader/loader_1st_128kb.bin
	rm -f ./2nd_loader/loader_2nd_param.bin
	make TARGET=loader_1st
	rm -f ./common/src/*.o
	make TARGET=loader_2nd
}

do_install() {
	install -d ${D}/boot
	install -m 755 ${S}/source/loader_1st.bin ${D}/boot/loader_1st.bin
	install -m 755 ${S}/source/loader_1st.elf ${D}/boot/loader_1st.elf
	install -m 755 ${S}/source/loader_1st_128kb.bin ${D}/boot/loader_1st_128kb.bin
	install -m 755 ${S}/source/loader_2nd.bin ${D}/boot/loader_2nd.bin
	install -m 755 ${S}/source/loader_2nd_param.bin ${D}/boot/loader_2nd_param.bin
	install -m 755 ${S}/source/loader_2nd.elf ${D}/boot/loader_2nd.elf
}

do_deploy() {
    install -d ${DEPLOYDIR}
	install -m 755 ${S}/source/loader_1st_128kb.bin ${DEPLOYDIR}/loader_1st_128kb.bin
	install -m 755 ${S}/source/loader_2nd.bin ${DEPLOYDIR}/loader_2nd.bin
	install -m 755 ${S}/source/loader_2nd_param.bin ${DEPLOYDIR}/loader_2nd_param.bin
	install -m 755 ${S}/source/loader_1st.elf ${DEPLOYDIR}/loader_1st.elf
	install -m 755 ${S}/source/loader_2nd.elf ${DEPLOYDIR}/loader_2nd.elf
}

addtask deploy before do_build after do_compile
