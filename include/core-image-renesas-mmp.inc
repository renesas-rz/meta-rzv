require core-image-renesas-mmp-base.inc

IMAGE_INSTALL_append = " \
		packagegroup-gstreamer1.0-plugins \
		packagegroup-wayland-community \
"
