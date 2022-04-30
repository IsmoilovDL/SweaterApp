<#import "parts/common.ftlh" as c>
<@c.page>

<div class="mb-5">
    <form method="get" action="/main" >
        <div class="form-inline">
            <label for="search">Search by tag</label>
            <input id="search" type="text" class="form-control col-md-5" name="filter" value="${filter?ifExists}">
            <input type="submit" class="btn btn-primary ml-3" value="Фильтр">
        </div>
    </form>
</div>

<#include "parts/messageEdit.ftlh" />
<#include "parts/messageList.ftlh"/>

</@c.page>
