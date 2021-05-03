package jp.masa.signalcontrollermod.gui;

import cpw.mods.fml.client.config.GuiCheckBox;
import jp.masa.signalcontrollermod.SignalControllerCore;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.masa.signalcontrollermod.gui.parts.GuiScreenCustom;
import jp.masa.signalcontrollermod.gui.signalcontroller.SignalType;
import jp.masa.signalcontrollermod.network.PacketSignalController;
import jp.masa.signalcontrollermod.utils.BlockPos;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.EnumUtils;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUISignalController extends GuiScreenCustom {
    private final TileEntitySignalController tile;
    private SignalType signalType;
    private List<BlockPos> nextSignalList;
    private List<BlockPos> displayPosList;
    private boolean above;
    private boolean last;
    private boolean repeat;
    private boolean reducedSpeed;
    private int currentScroll;

    public GUISignalController(TileEntitySignalController tile) {
        this.tile = tile;
        this.signalType = tile.getSignalType();
        this.nextSignalList = tile.getNextSignal();
        this.displayPosList = tile.getDisplayPos();
        this.above = tile.isAbove();
        this.last = tile.isLast();
        this.repeat = tile.isRepeat();
        this.reducedSpeed = tile.isReducedSpeed();
    }

    // 毎tick呼び出される
    // 文字描画だったりボタンの表示非表示の更新だった李をするところ
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTick) {
        super.drawScreen(mouseX, mouseY, partialTick);
        //文字の描画
        //横はthis.width
        //縦はthis.height
        //this.fontRendererObj.drawString("ここに文字", 横座標, 縦座標, 白なら0xffffff);

        this.fontRendererObj.drawString("SignalController", this.width / 2 - 200, 20, 0xffffff);
        this.fontRendererObj.drawString("SignalType", this.width / 2 - 120, this.height / 2 - 75 + this.currentScroll, 0xffffff);
        this.fontRendererObj.drawString("Option", this.width / 2 - 120, this.height / 2 - 50 + this.currentScroll, 0xffffff);
        this.fontRendererObj.drawString("last", this.width / 2 - 35, this.height / 2 - 50 + this.currentScroll, 0xffffff);
        this.fontRendererObj.drawString("repeat", this.width / 2 + 15, this.height / 2 - 50 + this.currentScroll, 0xffffff);
        this.fontRendererObj.drawString("Reduced Speed", this.width / 2 + 65, this.height / 2 - 50 + this.currentScroll, 0xffffff);
        this.fontRendererObj.drawString("x", this.width / 2 - 37, this.height / 2 - 25 + this.currentScroll, 0xffffff);
        this.fontRendererObj.drawString("y", this.width / 2 - 2, this.height / 2 - 25 + this.currentScroll, 0xffffff);
        this.fontRendererObj.drawString("z", this.width / 2 + 33, this.height / 2 - 25 + this.currentScroll, 0xffffff);

        int nHeight = this.height / 2 - 30 + this.currentScroll;
        for (int i = 0; i < this.nextSignalList.size(); i++) {
            this.fontRendererObj.drawString("nextSignal" + i, this.width / 2 - 120, nHeight += 25, 0xffffff);
        }
        for (int i = 0; i < this.displayPosList.size(); i++) {
            this.fontRendererObj.drawString("displayPos" + i, this.width / 2 - 120, nHeight += 25, 0xffffff);
        }
        this.fontRendererObj.drawString("above", this.width / 2 - 120, nHeight += 25, 0xffffff);

        for (Object o : this.buttonList) {
            GuiButton button = (GuiButton) o;
            if (button.id == 1) {
                button.displayString = I18n.format("SignalControllerMod.gui.signalType." + this.signalType.toString());
            }
        }
    }

    //チェックボックスも
    //ボタンはここ
    //this.buttonList.add(new GuiButton(id,横座標,縦座標,横長さ,縦長さ,文字列))
    @Override
    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(1, this.width / 2 - 40, this.height / 2 - 80 + this.currentScroll, 80, 20, ""));
        this.buttonList.add(new GuiCheckBox(1001, this.width / 2 - 50, this.height / 2 - 51 + this.currentScroll, "", this.last));
        this.buttonList.add(new GuiCheckBox(1002, this.width / 2, this.height / 2 - 51 + this.currentScroll, "", this.repeat));
        this.buttonList.add(new GuiCheckBox(1003, this.width / 2 + 50, this.height / 2 - 51 + this.currentScroll, "", this.reducedSpeed));
        this.buttonList.add(new GuiButton(21, this.width / 2 - 110, this.height - 30, 100, 20, "決定"));
        this.buttonList.add(new GuiButton(20, this.width / 2 + 10, this.height - 30, 100, 20, "キャンセル"));

        int nHeight = this.height / 2 - 10 + this.currentScroll;
        List<BlockPos> signalList = this.nextSignalList;
        for (int i = 0, signalListSize = signalList.size(); i < signalListSize; i++) {
            BlockPos nextSignal = signalList.get(i);
            this.addGuiTextField(String.valueOf(nextSignal.X), this.width / 2 - 50, nHeight, Byte.MAX_VALUE, 30);
            this.addGuiTextField(String.valueOf(nextSignal.Y), this.width / 2 - 15, nHeight, Byte.MAX_VALUE, 30);
            this.addGuiTextField(String.valueOf(nextSignal.Z), this.width / 2 + 20, nHeight, Byte.MAX_VALUE, 30);
            this.buttonList.add(new GuiButton(5000 + i, this.width / 2 + 55, nHeight, 20, 20, "+"));
            if (i != 0) {
                this.buttonList.add(new GuiButton(6000 + i, this.width / 2 + 80, nHeight, 20, 20, "-"));
            }
            nHeight += 25;
        }
        List<BlockPos> displayList = this.displayPosList;
        for (int i = 0, displayListSize = displayList.size(); i < displayListSize; i++) {
            BlockPos displayPos = displayList.get(i);
            this.addGuiTextField(String.valueOf(displayPos.X), this.width / 2 - 50, nHeight, Byte.MAX_VALUE, 30);
            this.addGuiTextField(String.valueOf(displayPos.Y), this.width / 2 - 15, nHeight, Byte.MAX_VALUE, 30);
            this.addGuiTextField(String.valueOf(displayPos.Z), this.width / 2 + 20, nHeight, Byte.MAX_VALUE, 30);
            this.buttonList.add(new GuiButton(7000 + i, this.width / 2 + 55, nHeight, 20, 20, "+"));
            if (i != 0) {
                this.buttonList.add(new GuiButton(8000 + i, this.width / 2 + 80, nHeight, 20, 20, "-"));
            }
            nHeight += 25;
        }
//        nHeight += 25;
        this.buttonList.add(new GuiCheckBox(1000, this.width / 2 - 6, nHeight + 5, "", this.above));
    }

    // キーボード入力時のevent
    @Override
    public void keyTyped(char par1, int par2) {
        super.keyTyped(par1, par2);
        this.textFieldList.forEach(textField -> textField.textboxKeyTyped(par1, par2));
    }

    // button押したときのevent
    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 20) {  //キャンセル
            this.mc.displayGuiScreen(null);
        } else if (button.id == 21) {    //決定
            this.sendPacket();
            this.mc.displayGuiScreen(null);
        } else if (button.id == 1) { //signalTypeボタン
            this.signalType = (SignalType) this.getNextEnum(this.signalType);
        } else if (button.id >= 8000) {
            this.saveValue();
            this.displayPosList.remove(button.id - 8000);
            this.initGui();
        } else if (button.id >= 7000) {
            this.saveValue();
            this.displayPosList.add(button.id - 7000 + 1, new BlockPos());
            this.initGui();
        } else if (button.id >= 6000) {
            this.saveValue();
            this.nextSignalList.remove(button.id - 6000);
            this.initGui();
        } else if (button.id >= 5000) {
            this.saveValue();
            this.nextSignalList.add(button.id - 5000 + 1, new BlockPos());
            this.initGui();
        }
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int i0 = Mouse.getEventDWheel();
        if (i0 != 0) {
            i0 = (i0 > 0) ? 25 : -25;
            this.scroll(i0);
        }
    }

    private void scroll(int par1) {
        this.currentScroll = Math.min(Math.max(this.currentScroll + par1, -this.width), 0);
        this.saveValue();
        this.initGui();
    }

    private void sendPacket() {
        this.saveValue();
        SignalControllerCore.NETWORK_WRAPPER.sendToServer(new PacketSignalController(
                this.tile,
                this.signalType,
                this.last,
                this.repeat,
                this.reducedSpeed,
                this.nextSignalList,
                this.displayPosList,
                this.above
        ));
    }

    private void saveValue() {
        this.last = ((GuiCheckBox) this.buttonList.get(1)).isChecked();
        this.repeat = ((GuiCheckBox) this.buttonList.get(2)).isChecked();
        this.reducedSpeed = ((GuiCheckBox) this.buttonList.get(3)).isChecked();
        int nextSignalSize = this.nextSignalList.size();
        for (int i = 0; i < nextSignalSize; i++) {
            this.nextSignalList.set(i, new BlockPos(
                    this.getIntGuiTextFieldText(3 * i),
                    this.getIntGuiTextFieldText(3 * i + 1),
                    this.getIntGuiTextFieldText(3 * i + 2)));
        }
        int displayPosSize = this.displayPosList.size();
        for(int i = 0; i < displayPosSize; i++) {
            this.displayPosList.set(i, new BlockPos(
                    this.getIntGuiTextFieldText(nextSignalSize * 3 + 3 * i),
                    this.getIntGuiTextFieldText(nextSignalSize * 3 + 3 * i + 1),
                    this.getIntGuiTextFieldText(nextSignalSize * 3 + 3 * i + 2)));
        }
        this.above = ((GuiCheckBox) this.buttonList.get(this.buttonList.size() - 1)).isChecked();
    }

    private int getIntGuiTextFieldText(int number) {
        String str = this.textFieldList.get(number).getText();
        int i = 0;
        if (str == null || str.isEmpty()) {
            return i;
        }

        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return i;
        }
        return i;
    }

    private void addGuiTextField(String str, int xPosition, int yPosition, int maxLength, int width) {
        GuiTextField text = new GuiTextField(this.fontRendererObj, xPosition, yPosition, width, 20);
        text.setFocused(false);
        text.setMaxStringLength(maxLength);
        text.setText(str);
        this.textFieldList.add(text);
    }

    public Enum getNextEnum(Enum e) {
        List enumList = EnumUtils.getEnumList(e.getDeclaringClass());
        int index = enumList.indexOf(e);
        return (Enum) enumList.get(enumList.size() > index + 1 ? index + 1 : 0);
    }
}
