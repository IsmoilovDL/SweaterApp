<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="form-text text-muted col-md-2"> User Name :  </label>
            <input type="text" name="username" class="form-control col-md-4" placeholder="User name"/>
        </div>
        <div class="form-group row">
            <label class="form-text text-muted col-md-2"> Password: </label>
            <input type="password" name="password" class="form-control col-md-4" placeholder="password"/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <div class="form-group row">
            <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else>Sign In</#if></button>
        </div>
    </form>
    <div>
        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
    </div>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <input type="submit" value="Sign Out" class="btn btn-primary"/>
    </form>
</#macro>