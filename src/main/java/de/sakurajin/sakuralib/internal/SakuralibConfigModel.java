package de.sakurajin.sakuralib.internal;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.SectionHeader;

@Modmenu(modId = "sakuralib")
@Config(name = "SakuraLib", wrapperName = "SakuraLibConfig")
public class SakuralibConfigModel {

    public boolean DEBUG = false;

    public boolean ALWAYS_ADD_EXAMPLE_DATA = false;
}
