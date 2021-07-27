do_install_append_rzg2l() {
	# These files is provided in other recipes
	rm -rf ${D}/${libdir}/libwayland-egl*
	rm -rf ${D}/${libdir}/pkgconfig/wayland-egl*
}

do_install_append_rzv2l() {
       # These files is provided in other recipes
       rm -rf ${D}/${libdir}/libwayland-egl*
       rm -rf ${D}/${libdir}/pkgconfig/wayland-egl*
}
