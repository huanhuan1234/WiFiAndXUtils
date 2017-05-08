package bawei.com.huangminghuan;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.xutils.x;

import java.io.File;

/**
 * Created by huanhuan on 2017/4/24.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);

        initData();
    }

    //2)图片通过ImageLoader加载，缓存到sdcard中，第二次进入时，不再重新加载图片，而是使用上次缓存的图片，图片展示casts节点下的数据（15分）
    private void initData() {
        try {

            File cacheDir = StorageUtils.getOwnCacheDirectory(this, "/SD");
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .threadPoolSize(3)//线程池内加载的数量
                    .discCacheFileNameGenerator(new Md5FileNameGenerator())//diskCache() and diskCacheFileNameGenerator()调用相互重叠
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .memoryCache(new LruMemoryCache((int) (6 * 1024 * 1024)))
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000))//设置超时时间
                    .build();
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
