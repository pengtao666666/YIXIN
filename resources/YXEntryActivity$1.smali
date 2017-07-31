.class Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;
.super Ljava/lang/Object;
.source "YXEntryActivity.java"

# interfaces
.implements Lim/yixin/gamesdk/api/YXGameCallbackListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->getToken(Ljava/lang/String;)V
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
        "Ljava/lang/Void;",
        ">;"
    }
.end annotation


# instance fields
.field final synthetic this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;


# direct methods
.method constructor <init>(Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;)V
    .locals 0
    .param p1, "this$0"    # Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    .prologue
    .line 114
    iput-object p1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public bridge synthetic callback(ILjava/lang/Object;)V
    .locals 0

    .prologue
    .line 114
    check-cast p2, Ljava/lang/Void;

    invoke-virtual {p0, p1, p2}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;->callback(ILjava/lang/Void;)V

    return-void
.end method

.method public callback(ILjava/lang/Void;)V
    .locals 4
    .param p1, "status"    # I
    .param p2, "aVoid"    # Ljava/lang/Void;

    .prologue
    const/4 v3, 0x3

    .line 121
    if-nez p1, :cond_0

    .line 123
    :try_start_0
    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v1

    iget-object v1, v1, Lcom/kf/framework/AgentOfyixin;->api:Lim/yixin/gamesdk/api/YXGameApi;

    new-instance v2, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1$1;

    invoke-direct {v2, p0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1$1;-><init>(Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;)V

    invoke-interface {v1, v2}, Lim/yixin/gamesdk/api/YXGameApi;->getAccountInfo(Lim/yixin/gamesdk/api/YXGameCallbackListener;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    .line 155
    :goto_0
    return-void

    .line 144
    :catch_0
    move-exception v0

    .line 145
    .local v0, "e":Ljava/lang/Exception;
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    .line 146
    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v1

    iget-object v1, v1, Lcom/kf/framework/AgentOfyixin;->loginLisenner:Lcom/kf/framework/callback/UserCallback;

    const-string v2, "\u767b\u5f55\u5931\u8d25"

    invoke-virtual {v1, v3, v2}, Lcom/kf/framework/callback/UserCallback;->onFail(ILjava/lang/String;)V

    .line 147
    iget-object v1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-virtual {v1}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    goto :goto_0

    .line 152
    .end local v0    # "e":Ljava/lang/Exception;
    :cond_0
    invoke-static {}, Lcom/kf/framework/AgentOfyixin;->getInstance()Lcom/kf/framework/AgentOfyixin;

    move-result-object v1

    iget-object v1, v1, Lcom/kf/framework/AgentOfyixin;->loginLisenner:Lcom/kf/framework/callback/UserCallback;

    const-string v2, "\u767b\u5f55\u5931\u8d25"

    invoke-virtual {v1, v3, v2}, Lcom/kf/framework/callback/UserCallback;->onFail(ILjava/lang/String;)V

    .line 153
    iget-object v1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$1;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-virtual {v1}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    goto :goto_0
.end method
