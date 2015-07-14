package br.com.techfullit.mycluby.common.models;

public class MailTemplate {

    private String template;

    public MailTemplate(String name, String text) {
	StringBuffer template = new StringBuffer();

	template.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> ");
	template.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
	template.append("<head>");
	template.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
	template.append("<title>Mail Template</title>");
	template.append("<meta name='viewport' content='width=device-width, initial-scale=1.0' />");
	template.append("</head>");
	template.append("<body>");
	template.append("	<table border='0' cellpadding='0' cellspacing='0' width='100%'>");
	template.append("		<tr>");
	template.append("			<td style='padding: 0 0 30px 0;'>");
	template.append("");
	template.append("				<table align='center' border='0' cellpadding='0' cellspacing='0'");
	template.append("					width='600'");
	template.append("					style='border: 1px solid #cccccc; border-collapse: collapse;'>");
	template.append("					<tr>");
	template.append("						<td align='center' bgcolor='#4D90FD'");
	template.append("							style='padding: 10px 0 0px 0; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;color:#fff;'>");
	template.append("							<h1>MyCluby Social</h1>");
	template.append("						</td>");
	template.append("					</tr>");
	template.append("					<tr>");
	template.append("						<td bgcolor='#ffffff' style='padding: 40px 30px 40px 30px;'>");
	template.append("							<table border='0' cellpadding='0' cellspacing='0' width='100%'>");
	template.append("								<tr>");
	template.append("									<td");
	template.append("										style='color: #153643; font-family: Arial, sans-serif; font-size: 24px;'>");
	template.append("										<b>Olá, " + name + "</b>");
	template.append("									</td>");
	template.append("								</tr>");
	template.append("								<tr>");
	template.append("									<td");
	template.append("										style='padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;'>");
	template.append("										<p>" + text + "</p></td>");
	template.append("								</tr>");
	template.append("							</table>");
	template.append("						</td>");
	template.append("					</tr>");
	template.append("					<tr>");
	template.append("						<td bgcolor='#000' style='padding: 30px 30px 30px 30px;'>");
	template.append("							<table border='0' cellpadding='0' cellspacing='0' width='100%'>");
	template.append("								<tr>");
	template.append("									<td");
	template.append("										style='color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;'");
	template.append("										width='75%'>&reg; Curitiba, 2014<br /> <a");
	template.append("										href='#' style='color: #ffffff;'><font color='#ffffff'>MyCluby Social</font></a> mensagem automática, favor não responder.");
	template.append("									</td>");
	template.append("									<td align='right' width='25%'>");
	template.append("										<table border='0' cellpadding='0' cellspacing='0'>");
	template.append("											<tr>");
	template.append("												<td");
	template.append("													style='font-family: Arial, sans-serif; font-size: 12px; font-weight: bold;'>");
	template.append("													<a href='http://www.techfullit.com.br/'");
	template.append("													style='color: #ffffff;'> Techfull IT Services &copy; </a>");
	template.append("												</td>");
	template.append("												<td style='font-size: 0; line-height: 0;' width='20'>&nbsp;</td>");
	template.append("												<td");
	template.append("													style='font-family: Arial, sans-serif; font-size: 12px; font-weight: bold;'>");
	template.append("													</a>");
	template.append("												</td>");
	template.append("											</tr>");
	template.append("										</table>");
	template.append("									</td>");
	template.append("								</tr>");
	template.append("							</table>");
	template.append("						</td>");
	template.append("					</tr>");
	template.append("				</table>");
	template.append("");
	template.append("			</td>");
	template.append("		</tr>");
	template.append("	</table>");
	template.append("</body>");
	template.append("</html>");

	this.template = template.toString();
    }

    public String getTemplate() {
	if (template == null)
	    template = "";
	return this.template;
    }

}
