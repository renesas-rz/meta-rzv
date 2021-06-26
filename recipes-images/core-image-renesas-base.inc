# Basic packages
IMAGE_INSTALL_append = " \
    bash \
    v4l-utils \
    i2c-tools \
    coreutils \
    openssl \
    openssl-bin \
"

# Add overrides for selection 32 bit or 64 bit target
OVERRIDES .= ":${TARGET_SYS}"



# Renesas Basic packages for 32bit
BASIC_32BIT_PKGS = " \
    lib32-coreutils \
    lib32-libstdc++ \
"

# Installation for 32bit packages
IMAGE_INSTALL_append = " \
    ${@base_conditional("USE_32BIT_PKGS", "1", "${BASIC_32BIT_PKGS}", "", d)} \
"
