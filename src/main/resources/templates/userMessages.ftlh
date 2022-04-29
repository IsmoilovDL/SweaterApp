<#import "parts/common.ftlh" as c>
<@c.page>
    <#if isCurrantUser>
        <#include "parts/messageEdit.ftlh" />
    </#if>
    <#include "parts/messageList.ftlh"/>
</@c.page>