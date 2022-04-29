<div class="card-columns">
    <#list messages as message>
    <div class="card my-3">
        <#if message.filename??>
            <img class="card-img-top" src="/img/${message.filename}">
        </#if>
        <div class="m-2">
            ${message.text}
            <i>${message.tag}</i>
        </div>
        <div class="card-footer text-muted">
            <strong>${message.authorName}</strong>
        </div>
    </div>
<#else>
    No messages
    </#list>>
</div>
</div>