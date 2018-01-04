<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@page import="com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil"%>
<%@page import="com.azilen.payment.integration.util.Constant"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.kernel.util.StringPool"%>
<%@page import="com.azilen.payment.integration.model.Customer"%>
<%@page import="com.azilen.payment.integration.model.Merchant"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.model.User"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>

<liferay-theme:defineObjects />
<portlet:defineObjects />
