package de.sakurajin.sakuralib.util;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

@Modmenu(modId = "sakuralib")
@Config(name = "SakuraLib", wrapperName = "SakuraLibConfig")
public class SakuralibConfigModel {

    public boolean DEBUG = false;
}
