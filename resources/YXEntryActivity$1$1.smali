.class Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1$1;
.super Ljava/lang/Object;
.source "YXEntryActivity.java"

# interfaces
.implements Lim/yixin/gamesdk/api/YXGameCallbackListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;->callback(ILjava/lang/Void;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Lim/yixin/gamesdk/api/YXGameCallbackListener",
        "<",
        "Lim/yixin/gamesdk/meta/GameAccount;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$1:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;


# direct methods
.method constructor <init>(Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;)V
    .locals 0
    .param p1, "this$1"    # Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;

    .prologue
    .line 123
    iput-object p1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1$1;->this$1:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public callback(ILim/yixin/gamesdk/meta/GameAccount;)V
    .locals 5
    .param p1, "status"    # I
    .param p2, "account"    # Lim/yixin/gamesdk/meta/GameAccount;

    .prologue
    .line 127
    if-nez p1, :cond_0

    .line 128
    new-instance v1, Lorg/json/JSONObject;

    invoke-direct {v1}, Lorg/json/JSONObject;-><init>()V

    .line 130
    .local v1, "jsonObject":Lorg/json/JSONObject;
    :try_start_0
    const-string v2, "userid"

    invoke-virtual {p2}, Lim/yixin/gamesdk/meta/GameAccount;->getAccountId()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    .line 131
    const-string v2, "uname"

    invoke-virtual {p2}, Lim/yixin/gamesdk/meta/GameAccount;->getNick()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v1, v2, v3}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    .line 136
    :goto_0
    invoke-virtual {p2}, Lim/yixin/gamesdk/meta/GameAccount;->getAccountId()Ljava/lang/String;

    move-result-object v2

    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v3

    iget-object v3, v3, Lcom/kf/framework/AgentOfyixin;->loginLisenner:Lcom/kf/framework/callback/UserCallback;

    invoke-static {v2, v1, v3}, Lcom/kf/framework/UserWrapper;->getAccessToken(Ljava/lang/String;Lorg/json/JSONObject;Lcom/kf/framework/callback/UserCallback;)V

    .line 137
    iget-object v2, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1$1;->this$1:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;

    iget-object v2, v2, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-virtual {v2}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    .line 142
    .end local v1    # "jsonObject":Lorg/json/JSONObject;
    :goto_1
    return-void

    .line 132
    .restart local v1    # "jsonObject":Lorg/json/JSONObject;
    :catch_0
    move-exception v0

    .line 133
    .local v0, "e":Lorg/json/JSONException;
    invoke-virtual {v0}, Lorg/json/JSONException;->printStackTrace()V

    goto :goto_0

    .line 139
    .end local v0    # "e":Lorg/json/JSONException;
    .end local v1    # "jsonObject":Lorg/json/JSONObject;
    :cond_0
    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v2

    iget-object v2, v2, Lcom/kf/framework/AgentOfyixin;->loginLisenner:Lcom/kf/framework/callback/UserCallback;

    const/4 v3, 0x3

    const-string v4, "\u767b\u5f55\u5931\u8d25"

    invoke-virtual {v2, v3, v4}, Lcom/kf/framework/callback/UserCallback;->onFail(ILjava/lang/String;)V

    .line 140
    iget-object v2, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1$1;->this$1:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;

    iget-object v2, v2, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-virtual {v2}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    goto :goto_1
.end method

.method public bridge synthetic callback(ILjava/lang/Object;)V
    .locals 0

    .prologue
    .line 123
    check-cast p2, Lim/yixin/gamesdk/meta/GameAccount;

    invoke-virtual {p0, p1, p2}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1$1;->callback(ILim/yixin/gamesdk/meta/GameAccount;)V

    return-void
.end method
