package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.ColorLayout;
import net.semanticmetadata.lire.imageanalysis.features.global.JpegCoefficientHistogram;
import net.semanticmetadata.lire.indexers.parallel.ParallelIndexer;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import net.semanticmetadata.lire.searchers.ImageSearcher;



public class LIREapplication {
	public static void index(String indexpath,String imagepath) {
		File f = new File(indexpath);
		if (f.exists() && f.isDirectory()) {
			System.out.println("Indexing images in " + indexpath);
			
			ParallelIndexer indexer = new ParallelIndexer(6, indexpath, imagepath);
			
			indexer.addExtractor(ColorLayout.class);			
			indexer.run();
			System.out.println("Finished indexing.");
		} else {
			System.out.println("Input is not a directory or not exist.");
			System.exit(1);
		}
	}

	public static ArrayList<String> search(String imgpath,String indexpath,String num) throws IOException {
		ArrayList<String> result=new ArrayList<>();
		File f = new File(imgpath);
		if (f.exists()) {
			BufferedImage img = ImageIO.read(f);

			IndexReader ir = DirectoryReader.open(FSDirectory.open(Paths.get(indexpath)));
			ImageSearcher searcher = new GenericFastImageSearcher(Integer.parseInt(num), ColorLayout.class);

			// searching with a image file ...
			ImageSearchHits hits = searcher.search(img, ir);
			// searching with a Lucene document instance ...
			for (int i = 0; i < hits.length(); i++) {
				String fileName = ir.document(hits.documentID(i)).getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER)[0];
				//System.out.println(hits.score(i) + ": \t" + fileName);
				result.add(fileName);
			}
		} else {
			System.out.println("Input is not a image or not exist.");
			System.exit(1);
		}
		return result;
	}
}
