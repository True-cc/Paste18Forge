/*
 * Copyright (c) 2018 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package uwu.smsgamer.pasteclient.utils;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.*;
import uwu.smsgamer.pasteclient.PasteClient;

@SideOnly(Side.CLIENT)
public class ChatUtils {
    public static final String PRIMARY_COLOR = "§7";
    public static final String SECONDARY_COLOR = "§1";
    private static final String PREFIX = PRIMARY_COLOR + "[" + SECONDARY_COLOR + PasteClient.CLIENT_NAME + PRIMARY_COLOR + "] ";

    public static void send(String s, Object... o) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        s = f(s, o);
        JsonObject object = new JsonObject();
        object.addProperty("text", s);
        Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(object.toString()));
    }

    public static void success(String s, Object... o) {
        info(s, o);
    }

    public static void info(String s, Object... o) {
        send(PREFIX + s, o);
    }

    public static String f(String s, Object... o) {
        return String.format(s, o);
    }
}
