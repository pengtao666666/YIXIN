.class public Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;
.super Lim/yixin/gamesdk/api/BaseYXGameEntryActivity;
.source "YXEntryActivity.java"


# static fields
.field private static final TAG:Ljava/lang/String; = "KuaifaYiXin.YXEntryActivity"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 34
    invoke-direct {p0}, Lim/yixin/gamesdk/api/BaseYXGameEntryActivity;-><init>()V

    return-void
.end method

.method private getToken(Ljava/lang/String;)V
    .locals 2
    .param p1, "code"    # Ljava/lang/String;

    .prologue
    .line 114
    invoke-virtual {p0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->getIYXGameApi()Lim/yixin/gamesdk/api/YXGameApi;

    move-result-object v0

    new-instance v1, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;

    invoke-direct {v1, p0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;-><init>(Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;)V

    invoke-interface {v0, p0, p1, v1}, Lim/yixin/gamesdk/api/YXGameApi;->fetchToken(Landroid/app/Activity;Ljava/lang/String;Lim/yixin/gamesdk/api/YXGameCallbackListener;)V

    .line 157
    return-void
.end method


# virtual methods
.method protected getIYXGameApi()Lim/yixin/gamesdk/api/YXGameApi;
    .locals 1

    .prologue
    .line 48
    invoke-static {}, Lim/yixin/gamesdk/api/YXGameApiFactory;->get()Lim/yixin/gamesdk/api/YXGameApi;

    move-result-object v0

    return-object v0
.end method

.method public onReq(Lim/yixin/sdk/api/BaseReq;)V
    .locals 6
    .param p1, "req"    # Lim/yixin/sdk/api/BaseReq;

    .prologue
    .line 164
    const-string v3, "KuaifaYiXin.YXEntryActivity"

    new-instance v4, Ljava/lang/StringBuilder;

    invoke-direct {v4}, Ljava/lang/StringBuilder;-><init>()V

    const-string v5, "onReq called: transaction="

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-object v5, p1, Lim/yixin/sdk/api/BaseReq;->transaction:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-static {v3, v4}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 165
    invoke-virtual {p1}, Lim/yixin/sdk/api/BaseReq;->getType()I

    move-result v3

    packed-switch v3, :pswitch_data_0

    .line 186
    :goto_0
    :pswitch_0
    return-void

    :pswitch_1
    move-object v1, p1

    .line 167
    check-cast v1, Lim/yixin/sdk/api/SendMessageToYX$Req;

    .line 168
    .local v1, "req1":Lim/yixin/sdk/api/SendMessageToYX$Req;
    goto :goto_0

    .end local v1    # "req1":Lim/yixin/sdk/api/SendMessageToYX$Req;
    :pswitch_2
    move-object v2, p1

    .line 170
    check-cast v2, Lim/yixin/sdk/api/ShowYXMessageFromYX$Req;

    .line 171
    .local v2, "req2":Lim/yixin/sdk/api/ShowYXMessageFromYX$Req;
    new-instance v0, Landroid/os/Handler;

    invoke-direct {v0}, Landroid/os/Handler;-><init>()V

    .line 172
    .local v0, "handler":Landroid/os/Handler;
    new-instance v3, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;

    invoke-direct {v3, p0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;-><init>(Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;)V

    invoke-virtual {v0, v3}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    goto :goto_0

    .line 165
    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_1
        :pswitch_0
        :pswitch_2
    .end packed-switch
.end method

.method public onResp(Lim/yixin/sdk/api/BaseResp;)V
    .locals 7
    .param p1, "resp"    # Lim/yixin/sdk/api/BaseResp;

    .prologue
    const/4 v6, 0x3

    const/4 v5, 0x1

    .line 57
    const-string v2, "KuaifaYiXin.YXEntryActivity"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "onResp called: errCode="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget v4, p1, Lim/yixin/sdk/api/BaseResp;->errCode:I

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, ",errStr="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-object v4, p1, Lim/yixin/sdk/api/BaseResp;->errStr:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    const-string v4, ",transaction="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    iget-object v4, p1, Lim/yixin/sdk/api/BaseResp;->transaction:Ljava/lang/String;

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-static {v2, v3}, Landroid/util/Log;->i(Ljava/lang/String;Ljava/lang/String;)I

    .line 59
    invoke-virtual {p1}, Lim/yixin/sdk/api/BaseResp;->getType()I

    move-result v2

    packed-switch v2, :pswitch_data_0

    .line 103
    :goto_0
    return-void

    :pswitch_0
    move-object v0, p1

    .line 61
    check-cast v0, Lim/yixin/sdk/api/SendMessageToYX$Resp;

    .line 62
    .local v0, "resp1":Lim/yixin/sdk/api/SendMessageToYX$Resp;
    iget v2, v0, Lim/yixin/sdk/api/SendMessageToYX$Resp;->errCode:I

    packed-switch v2, :pswitch_data_1

    goto :goto_0

    .line 73
    :pswitch_1
    const-string v2, "\u53d1\u9001\u5931\u8d25"

    invoke-static {p0, v2, v5}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 64
    :pswitch_2
    const-string v2, "\u5206\u4eab\u6210\u529f"

    invoke-static {p0, v2, v5}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 67
    :pswitch_3
    const-string v2, "\u5206\u4eab\u5931\u8d25"

    invoke-static {p0, v2, v5}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 70
    :pswitch_4
    const-string v2, "\u7528\u6237\u53d6\u6d88"

    invoke-static {p0, v2, v5}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v2

    invoke-virtual {v2}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .end local v0    # "resp1":Lim/yixin/sdk/api/SendMessageToYX$Resp;
    :pswitch_5
    move-object v1, p1

    .line 78
    check-cast v1, Lim/yixin/sdk/api/SendAuthToYX$Resp;

    .line 83
    .local v1, "resp2":Lim/yixin/sdk/api/SendAuthToYX$Resp;
    iget v2, v1, Lim/yixin/sdk/api/SendAuthToYX$Resp;->errCode:I

    packed-switch v2, :pswitch_data_2

    :pswitch_6
    goto :goto_0

    .line 96
    :pswitch_7
    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v2

    iget-object v2, v2, Lcom/kf/framework/AgentOfyixin;->loginLisenner:Lcom/kf/framework/callback/UserCallback;

    const-string v3, "\u7528\u6237\u62d2\u7edd"

    invoke-virtual {v2, v6, v3}, Lcom/kf/framework/callback/UserCallback;->onFail(ILjava/lang/String;)V

    .line 98
    invoke-virtual {p0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    goto :goto_0

    .line 85
    :pswitch_8
    iget-object v2, v1, Lim/yixin/sdk/api/SendAuthToYX$Resp;->code:Ljava/lang/String;

    invoke-direct {p0, v2}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->getToken(Ljava/lang/String;)V

    goto :goto_0

    .line 88
    :pswitch_9
    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v2

    iget-object v2, v2, Lcom/kf/framework/AgentOfyixin;->loginLisenner:Lcom/kf/framework/callback/UserCallback;

    const-string v3, "\u767b\u5f55\u5931\u8d25"

    invoke-virtual {v2, v6, v3}, Lcom/kf/framework/callback/UserCallback;->onFail(ILjava/lang/String;)V

    .line 89
    invoke-virtual {p0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    goto :goto_0

    .line 92
    :pswitch_a
    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v2

    iget-object v2, v2, Lcom/kf/framework/AgentOfyixin;->loginLisenner:Lcom/kf/framework/callback/UserCallback;

    const-string v3, "\u7528\u6237\u53d6\u6d88"

    invoke-virtual {v2, v6, v3}, Lcom/kf/framework/callback/UserCallback;->onFail(ILjava/lang/String;)V

    .line 93
    invoke-virtual {p0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    goto :goto_0

    .line 59
    nop

    :pswitch_data_0
    .packed-switch 0x1
        :pswitch_0
        :pswitch_5
    .end packed-switch

    .line 62
    :pswitch_data_1
    .packed-switch -0x3
        :pswitch_1
        :pswitch_4
        :pswitch_3
        :pswitch_2
    .end packed-switch

    .line 83
    :pswitch_data_2
    .packed-switch -0x4
        :pswitch_7
        :pswitch_6
        :pswitch_a
        :pswitch_9
        :pswitch_8
    .end packed-switch
.end method
