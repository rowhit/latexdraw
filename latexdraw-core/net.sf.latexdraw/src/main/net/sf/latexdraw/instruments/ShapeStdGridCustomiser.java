package net.sf.latexdraw.instruments;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import net.sf.latexdraw.glib.models.interfaces.prop.IStdGridProp;
import net.sf.latexdraw.glib.models.interfaces.shape.IGroup;

import org.malai.javafx.instrument.JfxInstrument;

/**
 * This instrument modifies the parameters of grids and axes.<br>
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
 * 12/23/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ShapeStdGridCustomiser extends JfxInstrument {// extends ShapePropertyCustomiser {
	/** The field that sets the X-coordinate of the starting point of the grid. */
	@FXML protected TextField xStartS;

	/** The field that sets the Y-coordinate of the starting point of the grid. */
	@FXML protected TextField yStartS;

	/** The field that sets the X-coordinate of the ending point of the grid. */
	@FXML protected TextField xEndS;

	/** The field that sets the Y-coordinate of the ending point of the grid. */
	@FXML protected TextField yEndS;

	/** The field that sets the size of the labels of the grid. */
	@FXML protected TextField labelsSizeS;

	/** The field that sets the X-coordinate of the origin point of the grid. */
	@FXML protected TextField xOriginS;

	/** The field that sets the Y-coordinate of the origin point of the grid. */
	@FXML protected TextField yOriginS;

	@FXML protected TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	public ShapeStdGridCustomiser() {
		super();
	}


//	@Override
	protected void update(final IGroup gp) {
		if(gp.isTypeOf(IStdGridProp.class)) {
//			((MSpinner.MSpinnerNumberModel)xStartS.getModel()).setMaximumSafely(gp.getGridEndX());
//			((MSpinner.MSpinnerNumberModel)yStartS.getModel()).setMaximumSafely(gp.getGridEndY());
//			xStartS.setValueSafely(gp.getGridStartX());
//			yStartS.setValueSafely(gp.getGridStartY());
//			((MSpinner.MSpinnerNumberModel)xEndS.getModel()).setMinumunSafely(gp.getGridStartX());
//			((MSpinner.MSpinnerNumberModel)yEndS.getModel()).setMinumunSafely(gp.getGridStartY());
//			xEndS.setValueSafely(gp.getGridEndX());
//			yEndS.setValueSafely(gp.getGridEndY());
//			xOriginS.setValueSafely(gp.getOriginX());
//			yOriginS.setValueSafely(gp.getOriginY());
//			labelsSizeS.setValueSafely(gp.getLabelsSize());
		}
		else setActivated(false);
	}


//	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}


	@Override
	protected void initialiseInteractors() {
//		try{
//			addInteractor(new Spinner2ModifySelectionGridCoords(this));
//			addInteractor(new Spinner2ModifyPencilGridCoords(this));
//		}catch(InstantiationException | IllegalAccessException e){
//			BadaboomCollector.INSTANCE.add(e);
//		}
	}


//	/** The link that maps a spinner to an action that modifies the selected shapes. */
//	private static class Spinner2ModifySelectionGridCoords extends Spinner2ModifyGridCoords<ModifyShapeProperty> {
//		protected Spinner2ModifySelectionGridCoords(final ShapeStandardGridCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyShapeProperty.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setGroup(instrument.pencil.canvas().getDrawing().getSelection().duplicateDeep(false));
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.hand.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//	/** The link that maps a spinner to an action that modifies the pencil. */
//	private static class Spinner2ModifyPencilGridCoords extends Spinner2ModifyGridCoords<ModifyPencilParameter> {
//		protected Spinner2ModifyPencilGridCoords(final ShapeStandardGridCustomiser ins) throws InstantiationException, IllegalAccessException {
//			super(ins, ModifyPencilParameter.class);
//		}
//
//		@Override
//		public void initAction() {
//			super.initAction();
//			action.setPencil(instrument.pencil);
//		}
//
//		@Override
//		public boolean isConditionRespected() {
//			return instrument.pencil.isActivated() && super.isConditionRespected();
//		}
//	}
//
//
//	private abstract static class Spinner2ModifyGridCoords<A extends ShapePropertyAction> extends SpinnerForCustomiser<A, ShapeStandardGridCustomiser> {
//		protected Spinner2ModifyGridCoords(final ShapeStandardGridCustomiser ins, final Class<A> clazzAction) throws InstantiationException, IllegalAccessException {
//			super(ins, clazzAction);
//		}
//
//		@Override
//		public void initAction() {
//			if(isOriginSpinner())
//				action.setProperty(ShapeProperties.GRID_ORIGIN);
//			else if(isLabelSizeSpinner())
//				action.setProperty(ShapeProperties.GRID_SIZE_LABEL);
//			else if(isStartGridSpinner())
//				action.setProperty(ShapeProperties.GRID_START);
//			else
//				action.setProperty(ShapeProperties.GRID_END);
//		}
//
//		@Override
//		public void updateAction() {
//			if(isOriginSpinner())
//				action.setValue(ShapeFactory.createPoint(
//						Double.parseDouble(instrument.xOriginS.getValue().toString()), Double.parseDouble(instrument.yOriginS.getValue().toString())));
//			else if(isLabelSizeSpinner())
//				action.setValue(Integer.parseInt(instrument.labelsSizeS.getValue().toString()));
//			else if(isStartGridSpinner())
//				action.setValue(ShapeFactory.createPoint(
//						Double.parseDouble(instrument.xStartS.getValue().toString()), Double.parseDouble(instrument.yStartS.getValue().toString())));
//			else
//				action.setValue(ShapeFactory.createPoint(
//						Double.parseDouble(instrument.xEndS.getValue().toString()), Double.parseDouble(instrument.yEndS.getValue().toString())));
//		}
//
//
//		private boolean isStartGridSpinner() {
//			return interaction.getSpinner()==instrument.xStartS || interaction.getSpinner()==instrument.yStartS;
//		}
//
//		private boolean isEndGridSpinner() {
//			return interaction.getSpinner()==instrument.xEndS || interaction.getSpinner()==instrument.yEndS;
//		}
//
//		private boolean isLabelSizeSpinner() {
//			return interaction.getSpinner()==instrument.labelsSizeS;
//		}
//
//		private boolean isOriginSpinner() {
//			return interaction.getSpinner()==instrument.xOriginS || interaction.getSpinner()==instrument.yOriginS;
//		}
//
//
//		@Override
//		public boolean isConditionRespected() {
//			return isStartGridSpinner() || isEndGridSpinner() || isLabelSizeSpinner() || isOriginSpinner();
//		}
//	}
}
