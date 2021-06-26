require u-boot-common_${PV}.inc
require u-boot.inc

DEPENDS += "bc-native dtc-native"

UBOOT_URL = "git://github.com/renesas-rz/renesas-u-boot-cip.git"
BRANCH = "v2018.09/rzg2"

SRC_URI = "${UBOOT_URL};branch=${BRANCH}"
SRCREV = "d867a25a9e2b1a3e33ab3ae84c1a7512f51547c1"
PV = "v2018.09+git${SRCPV}"


