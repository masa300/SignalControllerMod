package jp.masa.atscontrollermod.gui.parts;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiScreenCustom extends GuiScreen {
	protected final List<GuiTextField> textFieldList = new ArrayList<>();

	@Override
	public void initGui() {
		super.initGui();
		super.buttonList.clear();
		this.textFieldList.clear();
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		this.textFieldList.forEach(GuiTextField::updateCursorCounter);
	}

	@Override
	public void mouseClicked(int x, int y, int btn) {
		super.mouseClicked(x, y, btn);
		this.textFieldList.forEach(guiTextField -> guiTextField.mouseClicked(x, y, btn));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		super.drawDefaultBackground();  // 背景
		this.textFieldList.forEach(GuiTextField::drawTextBox);
		super.drawScreen(mouseX, mouseY, partialTick);
	}

	@Override
	public boolean doesGuiPauseGame() { // GUI開いてるときにマイクラが止まるかどうか
		return false;
	}
}
