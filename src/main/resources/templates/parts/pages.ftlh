<#macro pager url page>
    <div class="mt-3">
        <ul class="pagination">
            <li class="page-item disabled">
                <a href="" class="page-link" tabindex="-1">Страницы</a>
            </li>
            <#list 1..page.getTotalPages() as p>
                <#if (p-1)==page.getNumber()>
                    <li class="page-item active">
                        <a href="#" class="page-link" tabindex="-1">${p}</a>
                    </li>
                <#else >
                    <li class="page-item ">
                        <a href="${url}?page=${p-1}size=${page.getSize()}" class="page-link" tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
        </ul>

        <ul class="pagination">
            <li class="page-item disabled">
                <a href="" class="page-link" tabindex="-1">Элементов на странице</a>
            </li>
            <#list [1,5,10,20] as c>
                <#if c==page.getSize()>
                    <li class="page-item active">
                        <a href="#" class="page-link" tabindex="-1">${c}</a>
                    </li>
                <#else >
                    <li class="page-item ">
                        <a href="${url}?page=${page.getNumber()}&size=${c}" class="page-link" tabindex="-1">${c}</a>
                    </li>
                </#if>
            </#list>
        </ul>
    </div>
</#macro>