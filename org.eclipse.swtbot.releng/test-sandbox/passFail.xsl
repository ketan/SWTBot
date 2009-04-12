<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output method="text"/>
     <xsl:template match="/">
<xsl:apply-templates select="testsuites/testsuite"/>
     </xsl:template>
<xsl:template match="testsuite">
<xsl:choose>
 <xsl:when test="@errors > 0 or @failures > 0">
  <xsl:text>
	test.status=failed
	test.failed=true
  </xsl:text>
 </xsl:when>
 <xsl:otherwise>
  <xsl:text>
	test.status=passed
	test.passed=true
  </xsl:text>
 </xsl:otherwise>
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
