.class public Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/AppRegister;
.super Lim/yixin/sdk/api/YXAPIBaseBroadcastReceiver;
.source "AppRegister.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 14
    invoke-direct {p0}, Lim/yixin/sdk/api/YXAPIBaseBroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method protected getAppId(Landroid/content/Context;)Ljava/lang/String;
    .locals 1
    .param p1, "context"    # Landroid/content/Context;

    .prologue
    .line 21
    invoke-static {}, Lim/yixin/gamesdk/api/YXGameApiFactory;->get()Lim/yixin/gamesdk/api/YXGameApi;

    move-result-object v0

    invoke-interface {v0}, Lim/yixin/gamesdk/api/YXGameApi;->getGameId()Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method protected onAfterYixinStart(Lim/yixin/sdk/channel/YXMessageProtocol;)V
    .locals 0
    .param p1, "protocol"    # Lim/yixin/sdk/channel/YXMessageProtocol;

    .prologue
    .line 32
    return-void
.end method
