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
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="Message">Message</label>
                <input id="Message" type="text" name="text" class="form-control col-md-5 ${(textError??)?string('is-invalid','')}"
                      value="<#if message??>${message.text}</#if>" placeholder="Введите сообщения">
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
                <label for="Tag">Tag</label>
                <input type="text" id="Tag" name="tag" class="form-control col-md-5" placeholder="Введите тег">
            </div>
            <div class="form-group">
                <label for="image">Download image</label>
                <input type="file" id="image" name="file">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}">
            <input type="submit" class="btn btn-primary" value="Отправить!">
        </form>
    </div>
<div>

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



</@c.page>
