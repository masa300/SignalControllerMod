# Signal Controller Mod
  RealTrainModで閉塞信号システムを簡単に組むことができるMODです。  
  GUIで信号機の設定、専用アイテムで信号機の登録を簡単に行うことができます。  
  レッドストーン入力で信号機を停止現示に切り替えることができ、現示レベルは自動的にリンクします。  


# 注意事項
  **当MODの導入によって生じた損害について、私は一切責任を負いません。**  
  レシピを登録していないため、サバイバルでは入手できません。  
  Minecraft ForgeやRealTrainModの導入方法はここでは説明しません。ご自身でお調べください。  
  
# 対応バージョン
  Minecraft 1.7.10

# 前提MOD
  Minecraft Forge 1.7.10  
  NGTLib 1.7.10.30以降  
  RealTrainMod 1.7.10.37以降  

# 導入方法
  1. Minecraft FrogeとRealTrainModを導入
  2. [こちら](https://github.com/masa300/SignalControllerMod/releases/latest)から当MODをダウンロードする
  3. ダウンロードしたMOD(jarファイル)をmodsフォルダーに入れる
  4. MODの導入が成功すると、クリエイティブタブにSignalControllerのタブが追加されます。

# 追加ブロック、アイテムの説明
- ## SignalControllerBlock
    信号機を制御するブロックです。  
    ブロックを右クリックで設定画面が開きます。
    このブロックはチャンクローダーを一緒に設置する必要があります。設置していない場合正しく現示されない可能性があります。  
    ↓以下のチャンクローダーは比較的軽く、大量に設置してもそこまで重くならないのでおすすめです。※PCスペックによる  
    http://www.9minecraft.net/dimensional-anchors-mod/  

  ### 設定画面の説明
  - #### signalType
    制御する信号機のタイプを登録します。  
    信号タイプは正しく登録してください。  
    登録が誤っていると、信号機が正しく現示しなかったり、無駄な処理が行われ重くなる原因になります。  

  - #### nextSignal
    次の閉塞信号の座標を設定し、リンクさせることができます。  
    ※場内信号など複数登録が可能  

  - #### displayPos
    制御する信号機が真上にない場合、座標を設定しリンクさせることができます。  
    指令所や中継信号機などに利用できます。  
    ※nextSignalとは異なり、displayPosは1つまで  

  - #### above
    制御する信号機が真上にある場合はチェック[x]を付けるだけでリンクさせることができます。

- ## 座標設定ツール
1. 座標設定ツールを持った状態で、RTMの信号機をShift＋右クリックすると、座標がアイテムに保存されます。
2. SignalControllerBlockをShift＋右クリックすると、1. で保存した座標をブロックに登録します。

    座標設定ツールにはNextSignal用(青)とDisplayPos(赤)の2種類があります。

# 協力
  当MODは、[hi03](https://twitter.com/hi03_s)様考案のATSControllerの仕組みをベースに制作させて頂きました。  
  MOD制作では[Kaiz_JP](https://twitter.com/Kaiz_JP)様に技術支援して頂きました。  
  この場を借りて御礼申し上げます。

# 連絡先
  Twitter: [@masa0300](https://twitter.com/masa0300)

# 履歴
|日付|バージョン|説明|備考|
|:---:|:---:|:---|:---|
|2020/12/25|SignalControllerMod-v1.2.0|正式リリース開始|座標を直接入力しても保存されないバグあり|
|2020/11/19|SignalControllerMod-v1.2.0|大量に設置すると動作が重くなるバグを修正||
|2020/11/10|SignalControllerMod-v1.1.1|aboveの設定が保存されないバグを修正||
|2020/11/05|SignalControllerMod-v1.1.0|nextSignalを複数登録可能に||
|2020/11/03|SignalControllerMod-v1.0.0|初リリース|サーバー限定で公開|
