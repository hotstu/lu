package github.hotstu.lu.engine;

import android.content.res.AssetManager;

import org.mozilla.javascript.commonjs.module.provider.ModuleSource;
import org.mozilla.javascript.commonjs.module.provider.UrlModuleSourceProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class AssetAndUrlModuleSourceProvider extends UrlModuleSourceProvider {

    private final URI mBaseURI;
    private final String mAssetDirPath;
    private final AssetManager mAssetManager;

    public AssetAndUrlModuleSourceProvider(String assetDirPath, List<URI> list) {
        super(list, null);
        mAssetDirPath = assetDirPath;
        mBaseURI = URI.create("file:///android_asset/" + assetDirPath);
        mAssetManager = ModuleApp.application.getAssets();
    }

    @Override
    protected ModuleSource loadFromPrivilegedLocations(String moduleId, Object validator) throws IOException, URISyntaxException {
        String moduleIdWithExtension = moduleId;
        if (!moduleIdWithExtension.endsWith(".js")) {
            moduleIdWithExtension += ".js";
        }
        try {
            return new ModuleSource(new InputStreamReader(mAssetManager.open(mAssetDirPath + "/" + moduleIdWithExtension)), null,
                    new URI(mBaseURI.toString() + "/" + moduleIdWithExtension), mBaseURI, validator);
        } catch (FileNotFoundException e) {
            return super.loadFromPrivilegedLocations(moduleId, validator);
        }
    }

}