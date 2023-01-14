package org.thymeleaf.examples.spring6.sayhello.dialect;

/**
 * ClassName : {Name}
 * Description :
 * Author : Paul Lin
 * Create : 2023/1/14$
 * Version :
 */
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class SayToPlanetAttributeTagProcessor extends AbstractAttributeTagProcessor {

    private static final String ATTR_NAME = "saytoplanet";
    private static final int PRECEDENCE = 10000;

    private static final String SAYTO_PLANET_MESSAGE = "msg.helloplanet";


    public SayToPlanetAttributeTagProcessor(final String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix,     // Prefix to be applied to name for matching
                null,              // No tag name: match any tag name
                false,             // No prefix to be applied to tag name
                ATTR_NAME,         // Name of the attribute that will be matched
                true,              // Apply dialect prefix to attribute name
                PRECEDENCE,        // Precedence (inside dialect's precedence)
                true);             // Remove the matched attribute afterwards
    }


    protected void doProcess(
            final ITemplateContext context, final IProcessableElementTag tag,
            final AttributeName attributeName, final String attributeValue,
            final IElementTagStructureHandler structureHandler) {

        /*
         * In order to evaluate the attribute value as a Thymeleaf Standard Expression,
         * we first obtain the parser, then use it for parsing the attribute value into
         * an expression object, and finally execute this expression object.
         */
        final IEngineConfiguration configuration = context.getConfiguration();

        final IStandardExpressionParser parser =
                StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression expression = parser.parseExpression(context, attributeValue);

        final String planet = (String) expression.execute(context);

        /*
         * This 'getMessage(...)' method will first try to resolve the message
         * from the configured Spring Message Sources (because this is a Spring
         * -enabled application).
         *
         * If not found, it will try to resolve it from a classpath-bound
         * .properties with the same name as the specified 'origin', which
         * in this case is this processor's class itself. This allows resources
         * to be packaged if needed in the same .jar files as the processors
         * they are used in.
         */
        final String i18nMessage =
                context.getMessage(
                        SayToPlanetAttributeTagProcessor.class,
                        SAYTO_PLANET_MESSAGE,
                        new Object[] {planet},
                        true);

        /*
         * Set the computed message as the body of the tag, HTML-escaped and
         * non-processable (hence the 'false' argument)
         */
        structureHandler.setBody(HtmlEscape.escapeHtml5(i18nMessage), false);

    }



}
