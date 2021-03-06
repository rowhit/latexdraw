package net.sf.latexdraw.view.svg;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.List;
import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGPolygonElement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestSVGPolygonElement extends AbstractTestSVGElement {
	@Test
	public void testEnableRendering() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20"); //$NON-NLS-1$
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		assertTrue(pl.enableRendering());
	}

	@Test
	public void testSetPoints() throws ParseException, MalformedSVGDocument {
		String path = "10,10 20,20"; //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20"); //$NON-NLS-1$
		SVGPolygonElement pl = new SVGPolygonElement(node, null);

		assertEquals(pl.getPoints(), path);
		pl.setPoints("10,10 20,20"); //$NON-NLS-1$
		assertEquals(2, pl.getPoints2D().size());
		assertEquals(new Point2D.Double(10, 10), pl.getPoints2D().get(0));
		assertEquals(new Point2D.Double(20, 20), pl.getPoints2D().get(pl.getPoints2D().size() - 1));
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testSetPointsFail() throws ParseException, MalformedSVGDocument {
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		pl.setPoints("10,,20fdsf"); //$NON-NLS-1$
	}

	@Test
	public void testGetPoints() throws MalformedSVGDocument, ParseException {
		String path = "10,10 20,20"; //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20"); //$NON-NLS-1$
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	public void testGetPoints2D() throws MalformedSVGDocument, ParseException {
		String path = "	  10\t ,\n	10 	\t 	20 \t\n\t\r,	\n20 	\r30,30	\n"; //$NON-NLS-1$

		node.setAttribute(SVGAttributes.SVG_POINTS, path);
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		List<Point2D> pts;

		pts = pl.getPoints2D();
		assertNotNull(pts);
		assertEquals(3, pts.size());
		assertEquals(new Point2D.Double(10, 10), pts.get(0));
		assertEquals(new Point2D.Double(20, 20), pts.get(1));
		assertEquals(new Point2D.Double(30, 30), pts.get(pts.size() - 1));
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument, ParseException {
		new SVGPolygonElement(null, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument, ParseException {
		new SVGPolygonElement(node, null);
	}

	@SuppressWarnings("unused")
	@Test
	public void testContructorOK1() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, ""); //$NON-NLS-1$
		new SVGPolygonElement(node, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = ParseException.class)
	public void testContructorFail5() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "dsqdgfd"); //$NON-NLS-1$
		new SVGPolygonElement(node, null);
	}

	@SuppressWarnings("unused")
	@Test
	public void testContructorOK2() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10"); //$NON-NLS-1$
		new SVGPolygonElement(node, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = ParseException.class)
	public void testContructorFail7() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, ","); //$NON-NLS-1$
		new SVGPolygonElement(node, null);
	}

	@SuppressWarnings("unused")
	@Test
	public void testContructorOK3() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20"); //$NON-NLS-1$
		new SVGPolygonElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_POLYGON;
	}
}
