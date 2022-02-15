require glibc_debian.bb
require recipes-core/glibc/glibc-initial.inc

# main glibc recipes muck with TARGET_CPPFLAGS to point into
# final target sysroot but we
# are not there when building glibc-initial
# so reset it here

TARGET_CPPFLAGS = ""

PV = "2.28"
