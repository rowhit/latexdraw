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
package net.sf.latexdraw.parsers.svg;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import net.sf.latexdraw.parsers.svg.parsers.SVGLengthParser;
import org.w3c.dom.Node;

/**
 * Defines the SVG element defining a picture.
 * @author Arnaud BLOUIN
 */
public class SVGImageElement extends SVGElement {
	/**
	 * {@link SVGImageElement#SVGImageElement(SVGDocument, String)}
	 * @param n The node.
	 * @param p The parent SVG element.
	 * @throws MalformedSVGDocument If the element is not well formed.
	 */
	public SVGImageElement(final Node n, final SVGElement p) throws MalformedSVGDocument {
		super(n, p);
	}



	/**
	 * Creates an SVG image.
	 * @param doc The owner document.
	 * @param pathSource The path of the picture.
	 * @since 0.1
	 */
	public SVGImageElement(final SVGDocument doc, final String pathSource) {
		super(doc);

		if(pathSource==null)
			throw new IllegalArgumentException();

		setNodeName(SVGElements.SVG_IMAGE);
		setAttribute("xlink:href", pathSource);//$NON-NLS-1$
	}



	/**
	 * @return The value of the X attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getX() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_X);
		double x;

		try { x = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(final ParseException e) { x = 0; }

		return x;
	}




	/**
	 * @return The value of the Y attribute (0 if there it does not exist or it is not a length).
	 * @since 0.1
	 */
	public double getY() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_Y);
		double y;

		try { y = v==null ? 0 : new SVGLengthParser(v).parseCoordinate().getValue(); }
		catch(final ParseException e) { y = 0; }

		return y;
	}


	/**
	 * @return The value of the <code>width</code> attribute.
	 * @since 0.1
	 */
	public double getWidth() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_WIDTH);
		double width;

		try { width = v==null ? Double.NaN : new SVGLengthParser(v).parseLength().getValue(); }
		catch(final ParseException e) { width = Double.NaN; }

		return width;
	}



	/**
	 * @return The value of the <code>height</code> attribute.
	 * @since 0.1
	 */
	public double getHeight() {
		final String v = getAttribute(getUsablePrefix()+SVGAttributes.SVG_HEIGHT);
		double height;

		try { height = v==null ? Double.NaN : new SVGLengthParser(v).parseLength().getValue(); }
		catch(final ParseException e) { height = Double.NaN; }

		return height;
	}



	/**
	 * @return The URI reference.
	 * @since 0.1
	 */
	public String getURI() {
		return getAttribute("xlink:href");//$NON-NLS-1$
	}



	@Override
	public boolean checkAttributes() {
		final double vWidth	= getWidth();
		final double vHeight	= getHeight();
        return !(Double.isNaN(vWidth) || Double.isNaN(vHeight) || vWidth < 0 || vHeight < 0);
    }




	@Override
	public boolean enableRendering() {
		if(getWidth()==0 || getHeight()==0 || getURI()==null)
			return false;

		try {
			final URI uri = new URI(getURI());
			final File f = new File(uri.getPath());

			if(!f.exists() || !f.canRead())
				return false;
		}
		catch(final URISyntaxException e) { return false; }

		return true;
	}
}
