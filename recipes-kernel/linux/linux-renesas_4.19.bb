DESCRIPTION = "Linux kernel for the RZG2 based board"

require recipes-kernel/linux/linux-yocto.inc
require include/cas-control.inc
require include/ecc-control.inc
require include/docker-control.inc

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}/:"
COMPATIBLE_MACHINE = "ek874|hihope-rzg2m|hihope-rzg2n|rzv2m"

KERNEL_URL = " \
    git://git.kernel.org/pub/scm/linux/kernel/git/cip/linux-cip.git"
BRANCH = "linux-4.19.y-cip"
SRCREV = "5b7dee96a2b4fffe481be81edc72fd104ed047ab"

SRC_URI = "${KERNEL_URL};protocol=https;nocheckout=1;branch=${BRANCH}"

SRC_URI_append += "\
  file://patches.scc \
"

SRC_URI_append_r9a09g011gbg += "\
  ${@base_conditional("ECC_FULL", "1", " file://patches/option_patch/0001-ARM64-DTS-cat874-reduce-mem-to-960M-when-enable-DRAM.patch ", "",d)} \
  file://patches/rzv2m_patch/0000-Makefile-rzv2m.patch \
  file://patches/rzv2m_patch/0000-headS-rzv2m.patch \
  file://patches/rzv2m_patch/0000-ptrace-rzv2m.patch \
  file://patches/rzv2m_patch/0001-defconfig-add-config-rzv2m.patch \
  file://patches/rzv2m_patch/0002-kconfig-platforms-add-config-rzv2m.patch \
  file://patches/rzv2m_patch/0003-dts-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0004-dts-Makefile-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0005-soc-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0006-include-dt-bindings-clock-rzv2m.patch \
  file://patches/rzv2m_patch/0007-clk-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0011-mmc-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0012-soc-sys-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0013-tty-serial-8250-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0014-include-asm-generic-serial-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0015-include-uapi-linux-serial-renesas-rzv2m.patch \
  file://patches/rzv2m_patch/0016-include-dt-bindings-power-rzv2m.patch \
  file://patches/rzv2m_patch/0017-modified-dts-defconfig-sdboot-rzv2m.patch \
  file://patches/rzv2m_patch/0018-modified-dts-CMAforDRP-rzv2m.patch \
  file://patches/rzv2m_patch/0019-modified-kernel-memoryarea.patch \
  file://patches/rzv2m_patch/0020_enabled_usb-xhci_and_i2c.patch \
  file://patches/rzv2m_patch/0000_enabled_read-only_mappings.patch \
  file://patches/rzv2m_patch/0021-enabled-udmabuf-rzv2m.patch \
  file://patches/rzv2m_patch/0022-enabled_eth-rzv2m.patch \
  file://patches/rzv2m_patch/0023-preserved-bootargs.patch \
  file://patches/rzv2m_patch/0025-chg-cma-area.patch \
  file://patches/rzv2m_patch/0027-mmc-enable-drv.patch \
  file://patches/rzv2m_patch/0028-enable-pmu-core0.patch \
  file://patches/rzv2m_patch/0030-change-ModelName.patch \
  file://patches/rzv2m_patch/0031-enable-i2c-drv.patch \
  file://patches/rzv2m_patch/0032-enable-pwm-drv.patch \
  file://patches/rzv2m_patch/0033-remake_dts.patch \
  file://patches/rzv2m_patch/0034_bug_fix_dts.patch \
  file://patches/rzv2m_patch/0035-chg-ArchTimerFrequency.patch \
  file://patches/rzv2m_patch/0036_add_hw_tim.patch \
  file://patches/rzv2m_patch/0037-added-cpg_drv.patch \
  file://patches/rzv2m_patch/0038-chg-fix-cpg_drv.patch \
  file://patches/rzv2m_patch/0039-Update-i2c-driver-source-code.patch \
  file://patches/rzv2m_patch/0040-Update-available-devices-evaluation-board.patch \
  file://patches/rzv2m_patch/0041-fixed-bug-cpgdriver-clocksource.patch \
  file://patches/rzv2m_patch/0042-fixed-bug-pwm.patch \
  file://patches/rzv2m_patch/0043-fixed-bug-hw_tim.patch \
  file://patches/rzv2m_patch/0044-modified-gicd-init.patch \
  file://patches/rzv2m_patch/0045-modified-gic-set-affinity.patch \
  file://patches/rzv2m_patch/0046-add-usbrole-setting.patch \
  file://patches/rzv2m_patch/0047_chg_cpg_clock_teble.patch \
  file://patches/rzv2m_patch/0048-add-switching-voltage-sd.patch \
  file://patches/rzv2m_patch/0049-enable-usb-peripheral.patch \
  file://patches/rzv2m_patch/0050-add-switching-voltage-emmc.patch \
  file://patches/rzv2m_patch/0051-usb-otg-support.patch \
  file://patches/rzv2m_patch/0052-remake-sdhi_core_drv.patch \
  file://patches/rzv2m_patch/0053-update-pwm-devicetree.patch \
  file://patches/rzv2m_patch/0054-update-i2c-devicetree.patch \
  file://patches/rzv2m_patch/0055-add-csi_driver.patch \
  file://patches/rzv2m_patch/0056-add-pfc_driver.patch \
  file://patches/rzv2m_patch/0057-pwm_disable_debug_log.patch \
  file://patches/rzv2m_patch/0058-remake-devicetree.patch \
  file://patches/rzv2m_patch/0059-fixed-csi_driver.patch \
  file://patches/rzv2m_patch/0060-Add-WDT-driver-source-for-RZV2M.patch \
  file://patches/rzv2m_patch/0061-Add-WDT-dts-config-for-RZV2M.patch \
  file://patches/rzv2m_patch/0062-fixed-bug-csi.patch \
  file://patches/rzv2m_patch/0063-apply-ddr-4gb.patch \
  file://patches/rzv2m_patch/0064-chg-gic-init.patch \
  file://patches/rzv2m_patch/0065-fixed-bug-usb-peripheral-driver.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"
LINUX_VERSION ?= "4.19.56-cip5"

PV = "${LINUX_VERSION}+git${SRCPV}"
PR = "r1"

KBUILD_DEFCONFIG = "defconfig"
KCONFIG_MODE = "alldefconfig"
SRC_URI_append = " \
    file://touch.cfg \
    file://gsx.cfg \
"

# Add SCHED_DEBUG config fragment to support CAS
SRC_URI_append = " \
    ${@base_conditional("USE_CAS", "1", " file://capacity_aware_migration_strategy.cfg", "",d)} \
"

# Install USB3.0 firmware to rootfs
USB3_FIRMWARE_V2 = "https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/r8a779x_usb3_v2.dlmem;md5sum=645db7e9056029efa15f158e51cc8a11"
USB3_FIRMWARE_V3 = "https://git.kernel.org/pub/scm/linux/kernel/git/firmware/linux-firmware.git/plain/r8a779x_usb3_v3.dlmem;md5sum=687d5d42f38f9850f8d5a6071dca3109"

SRC_URI_append = " \
    ${USB3_FIRMWARE_V2} \
    ${USB3_FIRMWARE_V3} \
    ${@bb.utils.contains('MACHINE_FEATURES','usb3','file://usb3.cfg','',d)} \
"

# Install regulatory database firmware to rootfs
REGULATORY_DB = "https://git.kernel.org/pub/scm/linux/kernel/git/sforshee/wireless-regdb.git/plain/regulatory.db?h=master-2019-06-03;md5sum=ce7cdefff7ba0223de999c9c18c2ff6f;downloadfilename=regulatory.db"
REGULATORY_DB_P7S = "https://git.kernel.org/pub/scm/linux/kernel/git/sforshee/wireless-regdb.git/plain/regulatory.db.p7s?h=master-2019-06-03;md5sum=489924336479385e2c35c21d10eb3ca2;downloadfilename=regulatory.db.p7s"

SRC_URI_append = " \
    ${REGULATORY_DB} \
    ${REGULATORY_DB_P7S} \
    file://wifi.cfg \
    file://bluetooth.cfg \
"

SRC_URI_append = "\
  ${@base_conditional("USE_DOCKER", "1", " file://docker.cfg ", "", d)} \
"

do_download_firmware () {
    install -m 755 ${WORKDIR}/r8a779x_usb3_v*.dlmem ${STAGING_KERNEL_DIR}/firmware
    install -m 755 ${WORKDIR}/regulatory* ${STAGING_KERNEL_DIR}/firmware
}

do_kernel_metadata_af_patch() {
  # need to recall do_kernel_metadata after do_patch for some patches applied to defconfig
  rm -f ${WORKDIR}/defconfig
  do_kernel_metadata
}

addtask do_download_firmware after do_configure before do_compile
addtask do_kernel_metadata_af_patch after do_patch before do_kernel_configme

# Fix race condition, which can causes configs in defconfig file be ignored
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}binutils:do_populate_sysroot"
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}gcc:do_populate_sysroot"
do_kernel_configme[depends] += "bc-native:do_populate_sysroot bison-native:do_populate_sysroot"

# Fix error: openssl/bio.h: No such file or directory
DEPENDS += "openssl-native"
