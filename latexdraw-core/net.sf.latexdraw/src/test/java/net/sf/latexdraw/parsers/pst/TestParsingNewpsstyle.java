package net.sf.latexdraw.parsers.pst;

import java.text.ParseException;
import net.sf.latexdraw.parsers.pst.parser.PSTParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestParsingNewpsstyle extends TestPSTParser {
	@Test
	public void testParseCommandExample() throws ParseException {
		parser.parsePSTCode("\\" + getCommandName() + getBasicCoordinates()); //$NON-NLS-1$
		assertEquals(1, PSTParser.errorLogs().size());
	}

	@Override
	public String getCommandName() {
		return "newpsstyle"; //$NON-NLS-1$
	}

	@Override
	public String getBasicCoordinates() {
		return "{mystyle}{linecolor=green,linestyle=dotted}"; //$NON-NLS-1$
	}
}
