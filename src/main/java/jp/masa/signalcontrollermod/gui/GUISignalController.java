package jp.masa.signalcontrollermod.gui;

import cpw.mods.fml.client.config.GuiCheckBox;
import jp.masa.signalcontrollermod.SignalControllerCore;
import jp.masa.signalcontrollermod.block.tileentity.TileEntitySignalController;
import jp.masa.signalcontrollermod.gui.signalcontroller.SignalType;
import jp.masa.signalcontrollermod.gui.parts.GuiScreenCustom;
import jp.masa.signalcontrollermod.network.PacketSignalController;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.apache.commons.lang3.EnumUtils;

import java.util.List;

public class GUISignalController extends GuiScreenCustom {

    private final TileEntitySignalController tile;
	private SignalType signalType;

    public GUISignalController(TileEntitySignalController tile) {
		this.tile = tile;
		this.signalType = tile.getSignalType();
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

		this.fontRendererObj.drawString("SignalController", this.width / 4, 20, 0xffffff);
		this.fontRendererObj.drawString("signalType", this.width / 2 - 120, this.height / 2 - 50, 0xffffff);
		this.fontRendererObj.drawString("x", this.width / 2 - 37, this.height / 2 - 25, 0xffffff);
		this.fontRendererObj.drawString("y", this.width / 2 - 2, this.height / 2 - 25, 0xffffff);
		this.fontRendererObj.drawString("z", this.width / 2 + 33, this.height / 2 - 25, 0xffffff);

		this.fontRendererObj.drawString("nextSignal", this.width / 2 - 120, this.height / 2 - 5, 0xffffff);
		this.fontRendererObj.drawString("displayPos", this.width / 2 - 120, this.height / 2 + 20, 0xffffff);
		this.fontRendererObj.drawString("above", this.width / 2 - 120, this.height / 2 + 45, 0xffffff);

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
		this.addGuiTextField(String.valueOf(this.tile.getNextSignal()[0][0]), this.width / 2 - 50, this.height / 2 - 10, Byte.MAX_VALUE, 30);
		this.addGuiTextField(String.valueOf(this.tile.getNextSignal()[0][1]), this.width / 2 - 15, this.height / 2 - 10, Byte.MAX_VALUE, 30);
		this.addGuiTextField(String.valueOf(this.tile.getNextSignal()[0][2]), this.width / 2 + 20, this.height / 2 - 10, Byte.MAX_VALUE, 30);
		this.addGuiTextField(String.valueOf(this.tile.getDisplayPos()[0]), this.width / 2 - 50, this.height / 2 + 15, Byte.MAX_VALUE, 30);
		this.addGuiTextField(String.valueOf(this.tile.getDisplayPos()[1]), this.width / 2 - 15, this.height / 2 + 15, Byte.MAX_VALUE, 30);
		this.addGuiTextField(String.valueOf(this.tile.getDisplayPos()[2]), this.width / 2 + 20, this.height / 2 + 15, Byte.MAX_VALUE, 30);
		this.buttonList.add(new GuiButton(1, this.width / 2 - 40, this.height / 2 - 55, 80, 20, ""));
//        this.buttonList.add(new GuiButton(2, this.width / 2 + 60, this.height / 2 - 10, 20, 20, "..."));
		this.buttonList.add(new GuiCheckBox(1000, this.width / 2 - 6, this.height / 2 + 45, "", tile.isAbove()));
		this.buttonList.add(new GuiButton(21, this.width / 2 - 110, this.height - 30, 100, 20, "決定"));
		this.buttonList.add(new GuiButton(20, this.width / 2 + 10, this.height - 30, 100, 20, "キャンセル"));
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
			this.mc.displayGuiScreen(null);
			this.sendPacket();
		} else if (button.id == 1) { //signalTypeボタン
			this.signalType = (SignalType) this.getNextEnum(this.signalType);
		}
	}

	private void sendPacket() {
		int[][] nextSignal = new int[][]{
				{this.getIntGuiTextFieldText(0), this.getIntGuiTextFieldText(1), this.getIntGuiTextFieldText(2)}
		};
		int[] displayPos = new int[]{
				this.getIntGuiTextFieldText(3), this.getIntGuiTextFieldText(4), this.getIntGuiTextFieldText(5)
		};
		boolean above = ((GuiCheckBox) this.buttonList.get(1)).isChecked();
        SignalControllerCore.NETWORK_WRAPPER.sendToServer(new PacketSignalController(this.tile, this.signalType, nextSignal, displayPos, above));
	}

	private int getIntGuiTextFieldText(int number) {
		String str = this.textFieldList.get(number).getText();
		int i = 0;
		if (str == null || str.equals("")) {
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
