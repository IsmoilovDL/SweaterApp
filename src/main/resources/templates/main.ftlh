<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <@l.logout/>
    </div>
<div>
    <form method="post">
        <input type="text" name="text" placeholder="Введите сообщения">
        <input type="text" name="tag" placeholder="Введите тег">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Отправить!">
    </form>
</div>
<div>
    Cписок сообщения
    <form method="post" action="filter">
        <input type="text" name="filter">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Фильтр">
    </form>
   <#list messages as message>
    <div>
       <b>${message.id}</b>
        ${message.text}
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
    </div>
    <#else>
    No messages
    </#list>>
</div>



</@c.page>
