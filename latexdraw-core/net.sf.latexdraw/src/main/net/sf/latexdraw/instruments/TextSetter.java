package net.sf.latexdraw.instruments;

import java.awt.event.KeyEvent;

import javax.swing.JLayeredPane;

import net.sf.latexdraw.actions.shape.AddShape;
import net.sf.latexdraw.actions.shape.ModifyShapeProperty;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;
import net.sf.latexdraw.glib.models.interfaces.shape.IPlot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IText;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.parsers.ps.PSFunctionParser;
import net.sf.latexdraw.ui.TextAreaAutoSize;

import org.malai.action.Action;
import org.malai.instrument.Instrument;
import org.malai.instrument.Interactor;
import org.malai.interaction.library.KeyTyped;
import org.malai.swing.action.library.ActivateInactivateInstruments;
import org.malai.swing.widget.MLayeredPane;

/**
 * This instrument allows to add and modify texts to the drawing.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 20/12/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class TextSetter extends Instrument {
	/** The text field. */
	protected TextAreaAutoSize textField;

	/** The pencil used to create shapes. */
	protected Pencil pencil;

	/**
	 * The point where texts are added. It may not corresponds with the location
	 * of the text field since the text field position is absolute (does not consider
	 * the zoom level).
	 */
	protected IPoint relativePoint;

	/** The text to modify throw this instrument. If it is not set, a new text will be created. */
	protected IText text;

	protected TextCustomiser custom;


	/**
	 * Creates the instrument.
	 * @param overlayedPanel The pane where the text field must be added.
	 * @throws NullPointerException If the given MLayeredPane is null.
	 * @since 3.0
	 */
	public TextSetter(final MLayeredPane overlayedPanel) {
		super();
		text		= null;
		textField	= new TextAreaAutoSize();
		overlayedPanel.add(textField, JLayeredPane.PALETTE_LAYER);
		textField.setVisible(false);
		addEventable(textField);
	}


	@Override
	public void onActionDone(final Action action) {
		super.onActionDone(action);
		if(custom!=null) custom.update();
	}


	/**
	 * Sets the text customiser.
	 * @param custom The instrument.
	 */
	public void setTestCustomiser(final TextCustomiser custom) {
		if(custom!=null) this.custom = custom;
	}




	/**
	 * Sets the text to modify throw this instrument.
	 * @param text The text to modify. Can be null (a new text will be created).
	 * @since 3.0
	 */
	public void setText(final IText text) {
		this.text = text;

		if(text!=null)
			textField.setText(text.getText());
	}


	/**
	 * @param pencil The pencil to set to the text setter.
	 * @since 3.0
	 */
	public void setPencil(final Pencil pencil) {
		this.pencil = pencil;
	}


	@Override
	protected void initialiseInteractors() {
		try{
			addInteractor(new Enter2SetText(this));
			addInteractor(new Enter2AddText(this));
			addInteractor(new Enter2CheckPlot(this));
			addInteractor(new KeyPress2Desactivate(this));
		}catch(InstantiationException | IllegalAccessException e){
			BadaboomCollector.INSTANCE.add(e);
		}
	}


	@Override
	public void setActivated(final boolean activated) {
		super.setActivated(activated);
		textField.setVisible(activated);
		if(activated)
			textField.requestFocusInWindow();
	}


	/**
	 * @return The text field used to set texts.
	 * @since 3.0
	 */
	public TextAreaAutoSize getTextField() {
		return textField;
	}


	/**
	 * @param relativePoint The point where texts are added. It may not corresponds with the location
	 * of the text field since the text field position is absolute (does not consider
	 * the zoom level).
	 * @since 3.0
	 */
	public void setRelativePoint(final IPoint relativePoint) {
		this.relativePoint = relativePoint;
	}
}


/**
 * This links maps a key press interaction to an action that desactivates the instrument.
 */
class KeyPress2Desactivate extends Interactor<ActivateInactivateInstruments, KeyTyped, TextSetter> {
	/**
	 * Creates the link.
	 */
	protected KeyPress2Desactivate(final TextSetter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ActivateInactivateInstruments.class, KeyTyped.class);
	}

	@Override
	public void initAction() {
		action.addInstrumentToInactivate(instrument);
	}

	@Override
	public boolean isConditionRespected() {
		final int key = interaction.getKey();
		// It is useless to check if another key is pressed because if it is the case, the interaction
		// is in state keyPressed.
		return key==KeyEvent.VK_ENTER && instrument.textField.isValidText() && !instrument.textField.getText().isEmpty() || key==KeyEvent.VK_ESCAPE;
	}
}


class Enter2SetText extends Interactor<ModifyShapeProperty, KeyTyped, TextSetter> {
	protected Enter2SetText(final TextSetter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, ModifyShapeProperty.class, KeyTyped.class);
	}

	@Override
	public void initAction() {
		final IGroup group = ShapeFactory.createGroup(false);
		group.addShape(instrument.text);
		action.setGroup(group);
		action.setProperty(ShapeProperties.TEXT);
		action.setValue(instrument.textField.getText());
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.text!=null && !instrument.textField.getText().isEmpty() && interaction.getKey()==KeyEvent.VK_ENTER;
	}
}


/**
 * This links maps a key press interaction to an action that adds a text to the drawing.
 */
class Enter2AddText extends Interactor<AddShape, KeyTyped, TextSetter> {
	/**
	 * Creates the link.
	 */
	protected Enter2AddText(final TextSetter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, AddShape.class, KeyTyped.class);
	}

	@Override
	public void initAction() {
		final IPoint textPosition = instrument.relativePoint==null ? ShapeFactory.createPoint(instrument.textField.getX(),
									instrument.textField.getY()+instrument.textField.getHeight()) : instrument.relativePoint;
		final IShape sh = instrument.pencil==null ? null : instrument.pencil.createShapeInstance();

		if(sh instanceof IText) {
			final IText text = (IText)sh;
			text.setPosition(textPosition.getX(), textPosition.getY());
			text.setText(instrument.textField.getText());
			action.setShape(text);
			action.setDrawing(instrument.pencil.canvas().getDrawing());
		}
	}

	@Override
	public boolean isConditionRespected() {
		return instrument.pencil.currentChoice()==EditionChoice.TEXT && instrument.text==null && !instrument.textField.getText().isEmpty() && interaction.getKey()==KeyEvent.VK_ENTER;
	}
}


class Enter2CheckPlot extends Interactor<AddShape, KeyTyped, TextSetter> {
	protected Enter2CheckPlot(final TextSetter ins) throws InstantiationException, IllegalAccessException {
		super(ins, false, AddShape.class, KeyTyped.class);
	}

	@Override
	public void initAction() {
		instrument.textField.setValid(true);
		final IPoint textPosition = instrument.relativePoint==null ? ShapeFactory.createPoint(instrument.textField.getX(),
									instrument.textField.getY()+instrument.textField.getHeight()) : instrument.relativePoint;
		final IShape sh = instrument.pencil==null ? null : instrument.pencil.createShapeInstance();

		if(sh instanceof IPlot) {
			final IPlot plot = (IPlot)sh;
			plot.setPosition(textPosition.getX(), textPosition.getY());
			plot.setEquation(instrument.textField.getText());
			action.setShape(plot);
			action.setDrawing(instrument.pencil.canvas().getDrawing());
		}
	}

	@SuppressWarnings("unused")
	@Override
	public boolean isConditionRespected() {
		boolean ok = instrument.pencil.currentChoice()==EditionChoice.PLOT && instrument.text==null && !instrument.textField.getText().isEmpty() && interaction.getKey()==KeyEvent.VK_ENTER;

		if(ok)
			try { new PSFunctionParser(instrument.textField.getText());}
			catch(InvalidFormatPSFunctionException|NumberFormatException ex){
				instrument.textField.setValid(false);
				ok = false;
			}

		return ok;
	}
}
