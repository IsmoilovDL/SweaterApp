<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    ${message?ifExists}
    <div class="mb-1">
        Login page
    </div>
<@l.login "/login" false/>


</@c.page>
