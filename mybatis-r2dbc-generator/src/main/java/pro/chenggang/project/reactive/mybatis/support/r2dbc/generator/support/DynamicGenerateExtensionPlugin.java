package pro.chenggang.project.reactive.mybatis.support.r2dbc.generator.support;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.runtime.dynamic.sql.elements.AbstractMethodGenerator;
import org.mybatis.generator.runtime.dynamic.sql.elements.FragmentGenerator;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.generator.core.PropertiesHolder;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.generator.option.LombokConfig;
import pro.chenggang.project.reactive.mybatis.support.r2dbc.generator.properties.GeneratorExtensionProperties;

import java.util.*;
import java.util.stream.Collectors;

public class DynamicGenerateExtensionPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if ((introspectedTable.getTargetRuntime() == IntrospectedTable.TargetRuntime.MYBATIS3)) {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
            interfaze.addAnnotation("@Mapper");
        }
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * auto generated");
        interfaze.addJavaDocLine(" * @author AutoGenerated");
        interfaze.addJavaDocLine(" */");
        boolean hasCommonSelectMapperClass = false;
        try {
            Class<?> aClass = Class.forName("cpro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonSelectMapper");
            hasCommonSelectMapperClass = true;
        } catch (ClassNotFoundException e) {
            //ignore CommonSelectMapper Class Not Found
        }
        if (hasCommonSelectMapperClass) {
            FullyQualifiedJavaType commonSelectMapperType = new FullyQualifiedJavaType("pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.CommonSelectMapper");
            interfaze.addSuperInterface(commonSelectMapperType);
            interfaze.addImportedType(commonSelectMapperType);
        }
        interfaze.getImportedTypes().removeIf(item -> StringUtils.equals(item.getFullyQualifiedName(), "org.mybatis.dynamic.sql.update.UpdateDSL<org.mybatis.dynamic.sql.update.UpdateModel>"));
        interfaze.getImportedTypes().removeIf(item -> StringUtils.equals(item.getFullyQualifiedName(), "org.mybatis.dynamic.sql.util.mybatis3.ReactiveMyBatis3Utils"));
        interfaze.addImportedType(new FullyQualifiedJavaType("reactor.core.publisher.Mono"));
        interfaze.addImportedType(new FullyQualifiedJavaType("reactor.core.publisher.Flux"));
        interfaze.addImportedType(new FullyQualifiedJavaType("pro.chenggang.project.reactive.mybatis.support.r2dbc.dynamic.ReactiveMyBatis3Utils"));
        return true;
    }

    @Override
    public boolean clientBasicInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        Optional<IntrospectedColumn> authIncrementColumn = introspectedTable.getPrimaryKeyColumns().stream()
                .filter(IntrospectedColumn::isAutoIncrement)
                .findFirst();
        authIncrementColumn.ifPresent(introspectedColumn -> {
            FullyQualifiedJavaType importOptionType = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options");
            if (!interfaze.getImportedTypes().contains(importOptionType)) {
                interfaze.addImportedType(importOptionType);
            }
            //@Options(useGeneratedKeys = true,keyProperty = "record.id")
            String optionsAnnotation = "@Options(useGeneratedKeys = true,keyProperty = \"record." + introspectedColumn.getActualColumnName() + "\")";
            method.addAnnotation(optionsAnnotation);
        });
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientBasicInsertMultipleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientBasicCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Long>"));
        return true;
    }

    @Override
    public boolean clientBasicDeleteMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientBasicSelectManyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Flux<" + introspectedTable.getBaseRecordType() + ">"));
        return true;
    }

    @Override
    public boolean clientBasicSelectOneMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<" + introspectedTable.getBaseRecordType() + ">"));
        return true;
    }

    @Override
    public boolean clientBasicUpdateMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientGeneralDeleteMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientGeneralCountMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Long>"));
        return true;
    }

    @Override
    public boolean clientGeneralSelectDistinctMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Flux<" + introspectedTable.getBaseRecordType() + ">"));
        return true;
    }

    @Override
    public boolean clientGeneralSelectMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Flux<" + introspectedTable.getBaseRecordType() + ">"));
        return true;
    }

    @Override
    public boolean clientSelectOneMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<" + introspectedTable.getBaseRecordType() + ">"));
        return true;
    }

    @Override
    public boolean clientGeneralUpdateMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientInsertMultipleMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        replaceMyBatis3UtilsLine(method);
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    /**
     * replace MyBatis3Utils --> ReactiveMyBatis3Utils
     * @param method
     */
    private void replaceMyBatis3UtilsLine(Method method) {
        List<String> bodyLines = method.getBodyLines();
        Optional<String> optionalReplacedValue = bodyLines.stream()
                .findFirst()
                .map(value -> StringUtils.replace(value, "MyBatis3Utils", "ReactiveMyBatis3Utils"));
        if (optionalReplacedValue.isPresent()) {
            bodyLines.remove(0);
            bodyLines.add(0, optionalReplacedValue.get());
        }
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        interfaze.addStaticImport("org.mybatis.dynamic.sql.SqlBuilder.isEqualTo");
        FullyQualifiedJavaType whereApplierType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.where.WhereApplier");
        interfaze.addImportedType(whereApplierType);
        method.setName("updateSelective");
        method.addParameter(new Parameter(whereApplierType, "whereApplier"));
        FullyQualifiedJavaType recordType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        String resultMapId = recordType.getShortNameWithoutTypeArguments() + "Result";
        String tableFieldName =
                JavaBeansUtil.getValidPropertyName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        FragmentGenerator fragmentGenerator = new FragmentGenerator.Builder()
                .withIntrospectedTable(introspectedTable)
                .withResultMapId(resultMapId)
                .withTableFieldName(tableFieldName)
                .build();
        method.getBodyLines().clear();
        method.addBodyLine("return update(c ->");
        method.addBodyLines(fragmentGenerator.getSetEqualWhenPresentLinesV2(introspectedTable.getNonPrimaryKeyColumns(),
                "    c", "    ", false));
        method.addBodyLine("    .applyWhere(whereApplier)");
        method.addBodyLine(");");
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientUpdateSelectiveColumnsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        method.setStatic(false);
        method.setDefault(true);
        List<String> bodyLines = method.getBodyLines();
        String tableFieldName = JavaBeansUtil.getValidPropertyName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        bodyLines.clear();
        List<String> lines = this.getUpdateByPrimaryKeyBodyLineWithAllNonPrimaryKeyColumns(tableFieldName, introspectedTable);
        bodyLines.addAll(lines);
        method.getParameters().removeIf(parameter -> StringUtils.equals(parameter.getType().getFullyQualifiedName(), "org.mybatis.dynamic.sql.update.UpdateDSL<org.mybatis.dynamic.sql.update.UpdateModel>"));
        interfaze.getImportedTypes().removeIf(parameter -> StringUtils.equals(parameter.getFullyQualifiedName(), "org.mybatis.dynamic.sql.update.UpdateDSL"));
        method.setName("updateAllByPrimaryKey");
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientUpdateAllColumnsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        // update all equalTo include id
        method.setStatic(false);
        method.setDefault(true);
        List<String> bodyLines = method.getBodyLines();
        String tableFieldName = JavaBeansUtil.getValidPropertyName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        bodyLines.clear();
        List<String> lines = this.getUpdateSelectiveByPrimaryKeyBodyLineWithAllNonPrimaryKeyColumns(tableFieldName, introspectedTable);
        bodyLines.addAll(lines);
        method.getParameters().removeIf(parameter -> StringUtils.equals(parameter.getType().getFullyQualifiedName(), "org.mybatis.dynamic.sql.update.UpdateDSL<org.mybatis.dynamic.sql.update.UpdateModel>"));
        interfaze.getImportedTypes().removeIf(parameter -> StringUtils.equals(parameter.getFullyQualifiedName(), "org.mybatis.dynamic.sql.update.UpdateDSL"));
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        method.setName("updateSelectiveByPrimaryKey");
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        // update all equalTo include id
        method.setStatic(false);
        method.setDefault(true);
        List<String> bodyLines = method.getBodyLines();
        String tableFieldName = JavaBeansUtil.getValidPropertyName(introspectedTable.getFullyQualifiedTable().getDomainObjectName());
        bodyLines.clear();
        List<String> lines = this.getUpdateWhereApplierBodyLineWithAllNonPrimaryKeyColumns(tableFieldName, introspectedTable);
        bodyLines.addAll(lines);
        FullyQualifiedJavaType whereApplierType = new FullyQualifiedJavaType("org.mybatis.dynamic.sql.where.WhereApplier");
        method.getParameters().removeIf(parameter -> StringUtils.equals(parameter.getType().getFullyQualifiedName(), "org.mybatis.dynamic.sql.update.UpdateDSL<org.mybatis.dynamic.sql.update.UpdateModel>"));
        interfaze.getImportedTypes().removeIf(parameter -> StringUtils.equals(parameter.getFullyQualifiedName(), "org.mybatis.dynamic.sql.update.UpdateDSL"));
        interfaze.addImportedType(whereApplierType);
        method.setName("updateAll");
        method.addParameter(new Parameter(whereApplierType, "whereApplier"));
        method.setReturnType(new FullyQualifiedJavaType("reactor.core.publisher.Mono<java.lang.Integer>"));
        return true;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        List<VisitableElement> elements = document.getRootElement().getElements();
        XmlElement xmlElement = (XmlElement) elements.get(0);
        List<VisitableElement> elementElements = xmlElement.getElements();
        elementElements.removeIf(visitableElement -> visitableElement instanceof TextElement);
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        XmlElement newXmlElement = new XmlElement(xmlElement);
        List<Attribute> attributeList = newXmlElement.getAttributes()
                .stream()
                .map(attribute -> {
                    if ("id".equals(attribute.getName())) {
                        return new Attribute(attribute.getName(), "TableResultMap");
                    }
                    return new Attribute(attribute.getName(), attribute.getValue());
                })
                .collect(Collectors.toList());
        newXmlElement.getAttributes().clear();
        attributeList.forEach(newXmlElement::addAttribute);
        List<String> columnNameWithTable = new ArrayList<>();
        List<XmlElement> subXmlElements = newXmlElement.getElements()
                .stream()
                .map(element -> {
                    XmlElement subXmlElement = new XmlElement((XmlElement) element);
                    List<Attribute> attributes = subXmlElement.getAttributes()
                            .stream()
                            .map(attribute -> {
                                if ("column".equals(attribute.getName())) {
                                    String columnWithTableName = tableName + "_" + attribute.getValue();
                                    columnNameWithTable.add(tableName + "." + attribute.getValue() + " AS " + columnWithTableName);
                                    return new Attribute(attribute.getName(), columnWithTableName);
                                }
                                return new Attribute(attribute.getName(), attribute.getValue());
                            })
                            .collect(Collectors.toList());
                    subXmlElement.getAttributes().clear();
                    attributes.forEach(subXmlElement::addAttribute);
                    return subXmlElement;
                })
                .collect(Collectors.toList());
        newXmlElement.getElements().clear();
        subXmlElements.forEach(newXmlElement::addElement);
        elements.add(newXmlElement);
        XmlElement columnSqlXmlElement = new XmlElement("sql");
        columnSqlXmlElement.addAttribute(new Attribute("id","columnNameWithTable"));
        String columnNameWithTableSql = String.join(", \n    ", columnNameWithTable);
        TextElement columnSqlContentElement = new TextElement(columnNameWithTableSql);
        columnSqlXmlElement.addElement(columnSqlContentElement);
        elements.add(columnSqlXmlElement);
        return true;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerApplyWhereMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processExtension(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processExtension(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processExtension(topLevelClass, introspectedTable);
        return false;
    }

    private void processExtension(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * auto generated");
        topLevelClass.addJavaDocLine(" * @author AutoGenerated");
        topLevelClass.addJavaDocLine(" */");
        topLevelClass.getFields().forEach(field -> field.setVisibility(JavaVisibility.PROTECTED));
        GeneratorExtensionProperties extensionProperties = PropertiesHolder.getInstance().getProperties();
        LinkedHashSet<LombokConfig> lombokConfigs = extensionProperties.getLombok();
        if (Objects.isNull(lombokConfigs) || lombokConfigs.isEmpty()) {
            return;
        }
        lombokConfigs.forEach(lombok -> {
            switch (lombok) {
                case Getter:
                    topLevelClass.addImportedType("lombok.Getter");
                    topLevelClass.addAnnotation("@Getter");
                    break;
                case Setter:
                    topLevelClass.addImportedType("lombok.Setter");
                    topLevelClass.addAnnotation("@Setter");
                    break;
                case ToString:
                    topLevelClass.addImportedType("lombok.ToString");
                    topLevelClass.addAnnotation("@ToString");
                    break;
                case Data:
                    topLevelClass.addImportedType("lombok.Data");
                    topLevelClass.addAnnotation("@Data");
                    break;
                case Builder:
                    topLevelClass.addImportedType("lombok.Builder");
                    topLevelClass.addAnnotation("@Builder");
                    break;
                case NoArgsConstructor:
                    topLevelClass.addImportedType("lombok.NoArgsConstructor");
                    topLevelClass.addAnnotation("@NoArgsConstructor");
                    break;
                case AllArgsConstructor:
                    topLevelClass.addImportedType("lombok.AllArgsConstructor");
                    topLevelClass.addAnnotation("@AllArgsConstructor");
                    break;
                case EqualsAndHashCode:
                    topLevelClass.addImportedType("lombok.EqualsAndHashCode");
                    topLevelClass.addAnnotation("@EqualsAndHashCode");
                    break;
                case AccessorsChain:
                    topLevelClass.addImportedType("lombok.experimental.Accessors");
                    topLevelClass.addAnnotation("@Accessors(chain = true)");
                    break;
                case AccessorsFluent:
                    topLevelClass.addImportedType("lombok.experimental.Accessors");
                    topLevelClass.addAnnotation("@Accessors(fluent = true)");
                    break;
                default:
            }
        });
        for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
            Field field = new Field(introspectedColumn.getActualColumnName().toUpperCase(), new FullyQualifiedJavaType(String.class.getName()));
            field.setVisibility(JavaVisibility.PUBLIC);
            field.setStatic(true);
            field.setFinal(true);
            field.setInitializationString("\"" + introspectedColumn.getJavaProperty() + "\"");
            context.getCommentGenerator().addClassComment(topLevelClass, introspectedTable);
            topLevelClass.addField(field);
            Field columnField = new Field("DB_" + introspectedColumn.getActualColumnName().toUpperCase(), new FullyQualifiedJavaType(String.class.getName()));
            columnField.setVisibility(JavaVisibility.PUBLIC);
            columnField.setStatic(true);
            columnField.setFinal(true);
            columnField.setInitializationString("\"" + introspectedColumn.getActualColumnName() + "\"");
            topLevelClass.addField(columnField);
        }
    }

    private List<String> getUpdateWhereApplierBodyLineWithAllNonPrimaryKeyColumns(String tableFieldName, IntrospectedTable introspectedTable) {
        List<String> lines = new ArrayList<>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns());
        Iterator<IntrospectedColumn> iter = columns.iterator();
        boolean first = true;
        String firstLinePrefix = "    c";
        String subsequentLinePrefix = "    ";
        lines.add("return update(c ->");
        while (iter.hasNext()) {
            IntrospectedColumn column = iter.next();
            String fieldName = AbstractMethodGenerator.calculateFieldName(tableFieldName, column);
            String methodName = JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType());
            String start;
            if (first) {
                start = firstLinePrefix;
                first = false;
            } else {
                start = subsequentLinePrefix;
            }
            String line = null;
            if (column.isNullable()) {
                line = start + ".set(" + fieldName
                        + ").equalTo(record::" + methodName
                        + ")";
            } else {
                line = start + ".set(" + fieldName
                        + ").equalToWhenPresent(record::" + methodName
                        + ")";
            }
            lines.add(line);
        }
        lines.add(subsequentLinePrefix + ".applyWhere(whereApplier)");
        lines.add(");");
        return lines;
    }

    private List<String> getUpdateByPrimaryKeyBodyLineWithAllNonPrimaryKeyColumns(String tableFieldName, IntrospectedTable introspectedTable) {
        List<String> lines = new ArrayList<>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns());
        Iterator<IntrospectedColumn> iter = columns.iterator();
        boolean first = true;
        String firstLinePrefix = "    c";
        String subsequentLinePrefix = "    ";
        lines.add("return update(c ->");
        while (iter.hasNext()) {
            IntrospectedColumn column = iter.next();
            String fieldName = AbstractMethodGenerator.calculateFieldName(tableFieldName, column);
            String methodName = JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType());
            String start;
            if (first) {
                start = firstLinePrefix;
                first = false;
            } else {
                start = subsequentLinePrefix;
            }
            String line = null;
            if (column.isNullable()) {
                line = start + ".set(" + fieldName
                        + ").equalTo(record::" + methodName
                        + ")";
            } else {
                line = start + ".set(" + fieldName
                        + ").equalToWhenPresent(record::" + methodName
                        + ")";
            }
            lines.add(line);
        }
        first = true;
        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            String fieldName = AbstractMethodGenerator.calculateFieldName(tableFieldName, column);
            String methodName = JavaBeansUtil.getGetterMethodName(
                    column.getJavaProperty(), column.getFullyQualifiedJavaType());
            if (first) {
                lines.add(subsequentLinePrefix + ".where(" + fieldName
                        + ", isEqualTo(record::" + methodName
                        + "))");
                first = false;
            } else {
                lines.add(subsequentLinePrefix + ".and(" + fieldName
                        + ", isEqualTo(record::" + methodName
                        + "))");
            }
        }
        lines.add(");");
        return lines;
    }

    private List<String> getUpdateSelectiveByPrimaryKeyBodyLineWithAllNonPrimaryKeyColumns(String tableFieldName, IntrospectedTable introspectedTable) {
        List<String> lines = new ArrayList<>();
        List<IntrospectedColumn> columns = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getNonPrimaryKeyColumns());
        Iterator<IntrospectedColumn> iter = columns.iterator();
        boolean first = true;
        String firstLinePrefix = "    c";
        String subsequentLinePrefix = "    ";
        lines.add("return update(c ->");
        while (iter.hasNext()) {
            IntrospectedColumn column = iter.next();
            String fieldName = AbstractMethodGenerator.calculateFieldName(tableFieldName, column);
            String methodName = JavaBeansUtil.getGetterMethodName(column.getJavaProperty(), column.getFullyQualifiedJavaType());
            String start;
            if (first) {
                start = firstLinePrefix;
                first = false;
            } else {
                start = subsequentLinePrefix;
            }
            String line = start + ".set(" + fieldName
                    + ").equalToWhenPresent(record::" + methodName
                    + ")";
            lines.add(line);
        }
        first = true;
        for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            String fieldName = AbstractMethodGenerator.calculateFieldName(tableFieldName, column);
            String methodName = JavaBeansUtil.getGetterMethodName(
                    column.getJavaProperty(), column.getFullyQualifiedJavaType());
            if (first) {
                lines.add(subsequentLinePrefix + ".where(" + fieldName
                        + ", isEqualTo(record::" + methodName
                        + "))");
                first = false;
            } else {
                lines.add(subsequentLinePrefix + ".and(" + fieldName
                        + ", isEqualTo(record::" + methodName
                        + "))");
            }
        }
        lines.add(");");
        return lines;
    }
}
