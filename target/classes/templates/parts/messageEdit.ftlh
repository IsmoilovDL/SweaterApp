<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Message editor
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
        <input type="hidden" name="id" value="<#if message??>${message.id}</#if>">

        <input type="submit" class="btn btn-primary" value="Save Message!">
    </form>
</div>
<div>