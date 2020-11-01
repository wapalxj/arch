 lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        var viewPrinter: HiViewPrinter? = null
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )


        //可视化
        viewPrinter = HiViewPrinter(this)
        viewPrinter.viewProvider?.showFloatingView()
        HiLogManager.getInstance().addPrinter(viewPrinter)

        mBinding.btn.setOnClickListener {
            printLog()
        }

    }

    fun printLog() {
        //自定义配置
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 0
            }
        }, E, "======", "55566666")

        HiLog.a("11111111111")

    }