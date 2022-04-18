<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
    <div>
        <@l.logout/>
    </div>
    <div>
        <a href="/user">User list</a>
    </div>
<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Введите сообщения">
        <input type="text" name="tag" placeholder="Введите тег">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Отправить!">
    </form>
</div>
<div>
    Cписок сообщения
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}">
        <input type="submit" value="Фильтр">
    </form>
   <#list messages as message>
    <div>
       <b>${message.id}</b>
        ${message.text}
        <i>${message.tag}</i>
        <strong>${message.authorName}</strong>
        <div>
            <#if message.filename??>
                <img src="/img/${message.filename}">
            </#if>
        </div>
    </div>
    <#else>
    No messages
    </#list>>
</div>



</@c.page>
