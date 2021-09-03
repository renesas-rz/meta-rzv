update_issue() {
    # Set BSP version
    BSP_VERSION="1.0.0"

    # Set SoC and Board info
    case "${MACHINE}" in
    rzv2m)
      BSP_SOC="RZV2M"
      BSP_BOARD="Evaluation Kit"
      ;;
    esac

    # Make issue file
    echo "BSP: ${BSP_SOC}/${BSP_BOARD}/${BSP_VERSION}" >> ${IMAGE_ROOTFS}/etc/issue
    echo "LSI: ${BSP_SOC}" >> ${IMAGE_ROOTFS}/etc/issue
    echo "Version: ${BSP_VERSION}" >> ${IMAGE_ROOTFS}/etc/issue
}
ROOTFS_POSTPROCESS_COMMAND += "update_issue; "
