# **rocko/rzv2m**  

This is a Yocto build layer that provides support for RZ/V2M from Renesas Electronics.  
Currently, the following boards are supported:  
- Board: RZ/V2M Evaluation Board Kit  

Refer to "Yocto Start-Up Guide" to know the detailed instructions. Get this document from Renesas web site.  

## Notice
**When you use this repository, you must rename the directory meta-rzv to meta-rzv2m. Otherwise, it may not build this package correctly.** 

## Patches
To contribute to this layer, you should email patches to renesas-rz@renesas.com. Please send .patch files as email attachments, not embedded in the email body.  

## Dependencies
This layer depends on:

- poky  
```bash
    URI: git://git.yoctoproject.org/poky
    layers: meta, meta-yocto, meta-yocto-bsp
    branch: rocko
    revision: 7e7ee662f5dea4d090293045f7498093322802cc
```
- meta-linaro  
```bash
    URI: git://git.linaro.org/openembedded/meta-linaro.git
    layers: meta-optee
    branch: rocko
    revision: 75dfb67bbb14a70cd47afda9726e2e1c76731885
```
- meta-openembedded  
```bash
    URI: git://git.openembedded.org/meta-openembedded
    layers: meta-oe
            meta-filesystems, meta-networking, meta-python (for Docker)
    branch: rocko
    revision: 352531015014d1957d6444d114f4451e241c4d23
```

- meta-gplv2
```bash
    URI:http://git.yoctoproject.org/cgit.cgi/meta-gplv2/
    layers: meta-gplv2
    branch: rocko
    revision: f875c60ecd6f30793b80a431a2423c4b98e51548
```

## Build Instructions
The following instructions require a Poky installation (or equivalent).  
Before clone required repositories, set the environment variable of the build directory.  
```bash
    $ export WORK=/home/<your username>/<your work directory>
```

The below git configuration is required:  
```bash
    $ git config --global user.email "you@example.com"
    $ git config --global user.name "Your Name"
```

Initialize a build using the 'oe-init-build-env' script in Poky. e.g.:  
```bash
    $ source poky/oe-init-build-env
```

Prepare default configuration files. :  
```bash
    $ cp $WORK/meta-rzv/docs/sample/conf/<board>/<toolchain>/*.conf ./conf/
```
\<board\> : rzv2m  
\<toolchain\> : linaro-gcc <br>

Build the target file system image using bitbake:
```bash
    $ bitbake core-image-<target>
```
\<target\>:minimal, bsp

After completing the images for the target machine will be available in the output.  

Images generated:
<table>
    <tr>
        <th>Generated files</th>
        <th>File name</th>
        <th>File name File stored path</th>
    </tr>
    <tr>
        <td>Linux kernel image</td>
        <td>Image</td>
        <td rowspan="3">$WORK/build/tmp/deploy/images/rzv2m</td>
    </tr>
    <tr>
        <td>Device tree file</td>
        <td>r9a09g011gbg-evaluation-board.dtb</td>
    </tr>
    <tr>
        <td>rootfs</td>
        <td>core-image-bsp-rzv2m.tar.bz2</td>
    </tr>
</table>

## Build Instructions for SDK

Use bitbake -c populate_sdk in $WORK/build for generating the toolchain SDK:
For 64-bit target SDK (aarch64):
```bash
    $ bitbake core-image-bsp -c populate_sdk
```
The SDK can be found in the output directory _'tmp/deploy/sdk'_
Usage of toolchain SDK: Install the SDK to the default: _/opt/poky/x.x_
For 64-bit target SDK:
```bash
    $ bash poky-glibc-x86_64-core-image-bsp-aarch64-toolchain-2.4.3.sh
```
For 64-bit application use environment script in _/opt/poky/x.x_
```bash
    $ source /opt/poky/2.4.3/environment-setup-aarch64-poky-linux
```

## Build configs
You can change some build configurations as below:  
- **GPLv3**: Choose allow GPLv3 packages or not
    - **Non-GPLv3 (default)**: Not allow GPLv3 license. All recipes that have the GPLv3 license will be downgraded to an older version that has an alternative license (done by meta-gplv2). In this setting, you can ignore the risk of strict license GPLv3.  
    ```bash
    INCOMPATIBLE_LICENSE = "GPLv3 GPLv3+"
    ```
    - **Allow GPLv3**: Allow GPLv3 license. If you are fine with the strict copy-left license GPLv3, you can use this setting to get a newer software version.   
    ```bash
    #INCOMPATIBLE_LICENSE = "GPLv3 GPLv3+"
    ```
