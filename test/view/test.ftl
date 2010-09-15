

<ul class="clearfix">
    <#list recommendations as product>
        <li>
        <div class="prodbox">
            <a href="${dgl.detailsLink(product)}" class="nobg"><img src="${imageServerUrl}${(urlHelper.getThumbnailUrl(rc, product))?default("")}" alt="${product.altText?default("")}" /></a>
            <p>
                <a href="${dgl.detailsLink(product)}">
                    <strong class="high">
                        ${(product.designerCategory.name)?default("")}<br />
                        ${(product.lineCategory.name)?default("")}
                    </strong>
                    <#if product.classificationClassAsString?has_content><br />${product.classificationClassAsString?default("")}</#if>
                </a>
                <br />
                <@dgl.showPriceComplete product, true/>
            </p>
        </div>
        </li>
    </#list>
</ul>