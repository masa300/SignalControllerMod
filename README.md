# Signal Controller Mod
  [![MCVer](https://img.shields.io/badge/Minecraft-1.7.10-brightgreen)](https://www.minecraft.net/)
  [![MCVer](https://img.shields.io/badge/Minecraft-1.12.2-brightgreen)](https://www.minecraft.net/)
  [![ForgeVer](https://img.shields.io/badge/Forge-10.13.4.1558-important)](https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.7.10.html)
  [![RTMVer](https://img.shields.io/badge/RealTrainMod-1.7.10.41-informational)](https://www.curseforge.com/minecraft/mc-mods/realtrainmod/files/3039063)  
  [![DLCount](https://img.shields.io/github/downloads/masa300/SignalControllerMod/total)](https://github.com/masa300/SignalControllerMod/releases)
  [![DLCountLatest](https://img.shields.io/github/downloads/masa300/SignalControllerMod/latest/total)](https://github.com/masa300/SignalControllerMod/releases/latest)  
  RealTrainModで閉塞信号システムを簡単に組むことができるMODです。  
  GUIで信号機の設定、専用アイテムで信号機の登録を簡単に行うことができます。  
  レッドストーン入力で信号機を停止現示に切り替えることができ、現示レベルは自動的にリンクします。  


# 注意事項
  **当MODの導入によって生じた損害について、私は一切責任を負いません。**  
  レシピを登録していないため、サバイバルでは入手できません。  
  Minecraft ForgeやRealTrainModの導入方法はここでは説明しません。ご自身でお調べください。  
  
# 導入方法
  1. Minecraft ForgeとRealTrainModを導入
  1. [こちら](https://github.com/masa300/SignalControllerMod/releases/latest)からSignalControllerMod-vX.X.X.jarをダウンロードする
  1. ダウンロードしたMOD(jarファイル)をmodsフォルダーに入れる
  1. MODの導入が成功すると、クリエイティブタブにSignalControllerのタブが追加されます。

# 追加ブロック、アイテムの説明
- ## SignalControllerBlock
    信号機を制御するブロックです。  
    ブロックを右クリックで設定画面が開きます。  
    このブロックはチャンクローダーを一緒に設置する必要があります。設置していない場合正しく現示されない可能性があります。  
    [Dimensional Anchors](http://immibis.com/mcmoddl/)というチャンクローダーは比較的軽く、大量に設置してもそこまで重くならないのでおすすめです。※PCスペックによる  
    

  ### 設定画面の説明
  - #### signalType
    制御する信号機のタイプを登録します。  
    信号タイプは正しく登録してください。  
    登録が誤っていると、信号機が正しく現示しなかったり、無駄な処理が行われ重くなる原因になります。  

  - #### nextSignal
    次の閉塞信号の座標を設定し、リンクさせることができます。  
    ※場内信号など複数登録が可能  

  - #### Option
     - #### last (終端)
       以前のバージョンでは、nextSignalを登録をしていない信号機は停止現示しかしなかったので、終点駅の場内信号にはダミーの信号を設置する必要がありました。
       lastモードを有効にすると、停止現示から1段階現示が上がるのでダミー信号を設置する必要がなくなりました。
      
      - #### repeat (繰り返し)
        通常の閉塞信号は、閉塞ごとに現示が変わりますが、閉塞区間が短く制動距離が足りない場合は同じ現示をするところがあります。
        repeatはその現示パターンを再現する機能になります。
        次の閉塞が注意、減速だった場合、同じ現示をします。
        
      - #### Reduced Speed (抑速)
        未実装
        
  - #### displayPos
    制御する信号機が真上にない場合、座標を設定しリンクさせることができます。  
    次に説明するaboveと併用することで、指令所や中継信号機などにも利用できます。  
    ※nextSignalとは異なり、displayPosは1つまで  

  - #### above
    制御する信号機が真上(32ブロック以内)にある場合はチェック[x]を付けるだけでリンクさせることができます。

- ## 座標設定ツール
  座標設定ツールにはNextSignal用(青)とDisplayPos(赤)の2種類があります。
  
  ### 信号機の登録
    ① 座標設定ツールを持った状態で、RTMの信号機をShift＋右クリックすると、座標がアイテムに保存されます。  
    ② SignalControllerBlockをShift＋右クリックすると、①で保存した座標をブロックに登録します。  

# 協力
  当MODは、[hi03](https://twitter.com/hi03_s)様考案のATSControllerの仕組みをベースに制作させて頂きました。  
  MOD制作では[Kaiz_JP](https://twitter.com/Kaiz_JP)様に技術支援して頂きました。  
  この場を借りて御礼申し上げます。

# 連絡先
  Twitter: [@masa300_fmkr](https://twitter.com/masa300_fmkr)

# 履歴
|日付|バージョン|説明|備考|
|:---:|:---:|:---|:---|
|2021/05/16|SignalControllerMod-v1.4.0|まれに座標登録時にクラッシュするバグを修正<br>aboveを新規登録できなくなった<br>GUI内で座標が2個以上あれば1個目でも消せるようになった||
|2021/03/22|SignalControllerMod-v1.3.1|まれに座標登録時にクラッシュしてワールドに入れなくなるバグを修正||
|2021/02/15|SignalControllerMod-v1.3.0|終端機能、並行現示機能追加、displayPosを複数登録可能になった||
|2020/12/25|SignalControllerMod-v1.2.1|座標設定が保存されないバグを修正|正式リリース開始|
|2020/11/19|SignalControllerMod-v1.2.0|大量に設置すると動作が重くなるバグを修正||
|2020/11/10|SignalControllerMod-v1.1.1|aboveの設定が保存されないバグを修正||
|2020/11/05|SignalControllerMod-v1.1.0|nextSignalを複数登録可能になった||
|2020/11/03|SignalControllerMod-v1.0.0|初リリース|サーバー限定で公開|
