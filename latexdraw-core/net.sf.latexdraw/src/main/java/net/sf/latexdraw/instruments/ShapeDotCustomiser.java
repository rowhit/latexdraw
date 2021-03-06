/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instruments;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import net.sf.latexdraw.actions.shape.ShapeProperties;
import net.sf.latexdraw.models.interfaces.prop.IDotProp;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.view.jfx.JFXWidgetCreator;

/**
 * This instrument modifies dot parameters.
 * @author Arnaud BLOUIN
 */
public class ShapeDotCustomiser extends ShapePropertyCustomiser implements Initializable, JFXWidgetCreator {
	/** Allows to define the size of a dot. */
	@FXML private Spinner<Double> dotSizeField;
	/** Allows the selection of a dot shape. */
	@FXML private ComboBox<DotStyle> dotCB;
	/** Changes the colour of the filling of the dot. */
	@FXML private ColorPicker fillingB;
	@FXML private TitledPane mainPane;

	/**
	 * Creates the instrument.
	 */
	ShapeDotCustomiser() {
		super();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		mainPane.managedProperty().bind(mainPane.visibleProperty());

		Map<DotStyle, Image> cache = new HashMap<>();
		cache.put(DotStyle.DOT, new Image("/res/dotStyles/dot.none.png"));
		cache.put(DotStyle.ASTERISK, new Image("/res/dotStyles/dot.asterisk.png"));
		cache.put(DotStyle.BAR, new Image("/res/dotStyles/dot.bar.png"));
		cache.put(DotStyle.DIAMOND, new Image("/res/dotStyles/dot.diamond.png"));
		cache.put(DotStyle.FDIAMOND, new Image("/res/dotStyles/dot.diamondF.png"));
		cache.put(DotStyle.O, new Image("/res/dotStyles/dot.o.png"));
		cache.put(DotStyle.OPLUS, new Image("/res/dotStyles/dot.oplus.png"));
		cache.put(DotStyle.OTIMES, new Image("/res/dotStyles/dot.ocross.png"));
		cache.put(DotStyle.PLUS, new Image("/res/dotStyles/dot.plus.png"));
		cache.put(DotStyle.X, new Image("/res/dotStyles/dot.cross.png"));
		cache.put(DotStyle.TRIANGLE, new Image("/res/dotStyles/dot.triangle.png"));
		cache.put(DotStyle.FTRIANGLE, new Image("/res/dotStyles/dot.triangleF.png"));
		cache.put(DotStyle.PENTAGON, new Image("/res/dotStyles/dot.pentagon.png"));
		cache.put(DotStyle.FPENTAGON, new Image("/res/dotStyles/dot.pentagonF.png"));
		cache.put(DotStyle.SQUARE, new Image("/res/dotStyles/dot.square.png"));
		cache.put(DotStyle.FSQUARE, new Image("/res/dotStyles/dot.squareF.png"));
		initComboBox(dotCB, cache, DotStyle.values());
	}

	@Override
	protected void update(final IGroup shape) {
		if(shape.isTypeOf(IDotProp.class)) {
			setActivated(true);
			dotSizeField.getValueFactory().setValue(shape.getDiametre());
			dotCB.getSelectionModel().select(shape.getDotStyle());
			fillingB.setDisable(!shape.isFillable());

			if(shape.isFillable()) {
				fillingB.setValue(shape.getDotFillingCol().toJFX());
			}
		}else {
			setActivated(false);
		}
	}

	@Override
	protected void setWidgetsVisible(final boolean visible) {
		mainPane.setVisible(visible);
	}

	@Override
	protected void configureBindings() throws InstantiationException, IllegalAccessException {
		addBinding(new Spinner4Pencil(this, dotSizeField, ShapeProperties.DOT_SIZE, false));
		addBinding(new Spinner4Selection(this, dotSizeField, ShapeProperties.DOT_SIZE, false));
		addBinding(new List4Pencil(this, dotCB, ShapeProperties.DOT_STYLE));
		addBinding(new List4Selection(this, dotCB, ShapeProperties.DOT_STYLE));
		addBinding(new ColourPicker4Selection(this, fillingB, ShapeProperties.DOT_FILLING_COL));
		addBinding(new ColourPicker4Pencil(this, fillingB, ShapeProperties.DOT_FILLING_COL));
	}
}
