package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGPathElement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSVGPathElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument {
		new SVGPathElement(null, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument {
		new SVGPathElement(node, null);
	}

	@SuppressWarnings("unused")
	@Test
	public void testContructorOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_D, "test"); //$NON-NLS-1$
		new SVGPathElement(node, null);
	}

	@Test
	public void testGetPathData() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_D, "M 0 0 L 10 10"); //$NON-NLS-1$
		SVGPathElement e = new SVGPathElement(node, null);
		assertEquals(e.getPathData(), "M 0 0 L 10 10"); //$NON-NLS-1$
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_PATH;
	}
}
