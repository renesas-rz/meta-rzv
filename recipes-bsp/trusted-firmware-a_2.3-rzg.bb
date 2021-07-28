LIC_FILES_CHKSUM = "file://docs/license.rst;md5=189505435dbcdcc8caa63c46fe93fa89"

require trusted-firmware-a.inc

URL = "git://git@github.com/renesas-rz/rzg_trusted-firmware-a.git"
BRANCH = "develop/rzv2l"
SRCREV = "a66bdcb01cd05bf6ace7538d875744da5d371d72"

SRC_URI += "${URL};protocol=ssh;branch=${BRANCH}"

PV = "2.3-rzg+git${SRCPV}"
PR = "r1"
