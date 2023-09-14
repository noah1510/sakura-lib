package de.sakurajin.sakuralib.impl;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;

@Modmenu(modId = "sakuralib")
@Config(name = "SakuraLib", wrapperName = "SakuraLibConfig")
public class SakuralibConfigModel {

    public boolean DEBUG = false;

    @SectionHeader("ModTracking")
    public boolean WARN_ON_MAJOR_VERSION_CHANGE = false;

    public boolean WARN_ON_MINOR_VERSION_CHANGE = false;
    public boolean WARN_ON_FORMAT_VERSION_UPDATE = false;
}
