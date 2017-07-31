.class Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;
.super Ljava/lang/Object;
.source "YXEntryActivity.java"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->onReq(Lim/yixin/sdk/api/BaseReq;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;


# direct methods
.method constructor <init>(Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;)V
    .locals 0
    .param p1, "this$0"    # Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    .prologue
    .line 172
    iput-object p1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    .prologue
    .line 175
    iget-object v1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-virtual {v1}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getPackageManager()Landroid/content/pm/PackageManager;

    move-result-object v1

    iget-object v2, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    .line 176
    invoke-virtual {v2}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->getPackageName()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Landroid/content/pm/PackageManager;->getLaunchIntentForPackage(Ljava/lang/String;)Landroid/content/Intent;

    move-result-object v0

    .line 177
    .local v0, "intent":Landroid/content/Intent;
    const/high16 v1, 0x4000000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 178
    const/high16 v1, 0x10000000

    invoke-virtual {v0, v1}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 179
    iget-object v1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-virtual {v1, v0}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->startActivity(Landroid/content/Intent;)V

    .line 180
    iget-object v1, p0, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity$2;->this$0:Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;

    invoke-virtual {v1}, Lcom/wyd/hero/yqlfc/cb1/mzw/yixin/yxapi/YXEntryActivity;->finish()V

    .line 181
    return-void
.end method
