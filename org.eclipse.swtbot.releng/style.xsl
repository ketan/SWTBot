<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet xmlns:xsl='http://www.w3.org/1999/XSL/Transform'
	version='1.0'>

	<xsl:output method="xml" version="1.0" omit-xml-declaration="no"
		standalone="yes" indent="yes" />

	<!--
		<xsl:template match="@*|node()"> <xsl:copy-of select="@*|node()[@kind
		= 'ADDED']"> <xsl:apply-templates select="@*|node()" /> </xsl:copy-of>
		</xsl:template>
	-->

	<!--
		<xsl:template match="@*|node()"> <xsl:for-each
		select="catalog/cd[artist='Bob Dylan']"> </xsl:for-each> <xsl:choose>
		<xsl:when test="delta[@kind!='ADDED']"> </xsl:when> <xsl:otherwise>
		<xsl:copy-of select="@*|node()"> <xsl:apply-templates
		select="@*|node()" /> </xsl:copy-of> </xsl:otherwise> </xsl:choose>
		</xsl:template>
	-->

	<xsl:template match="@*|node()">
		<xsl:for-each select="delta[@kind!='ADDED']">
			<xsl:copy-of select=".">
				<xsl:apply-templates select="@*|node()" />
			</xsl:copy-of>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
